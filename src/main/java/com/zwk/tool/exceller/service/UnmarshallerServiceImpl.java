package com.zwk.tool.exceller.service;

import com.zwk.tool.exceller.annotation.ExcelColumn;
import com.zwk.tool.exceller.annotation.ExcelSheet;
import com.zwk.tool.exceller.annotation.ExcelTable;
import com.zwk.tool.exceller.dto.ExcelContentLocator;
import com.zwk.tool.exceller.dto.FieldMapper;
import com.zwk.tool.exceller.util.ExcelUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFTable;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UnmarshallerServiceImpl implements UnmarshallerService {

    private final DataFormatter dataFormatter = new DataFormatter();
    private final SimpleDateFormat defaultDateFormat = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * Read excel file from input stream and get list of objects.
     * @param inputStream
     * @param type
     * @param <T>
     * @return
     * @throws Exception
     */
    @Override
    public <T> List<T> fromExcel (InputStream inputStream, Class<T> type) throws Exception {

        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        XSSFSheet xssfSheet = getSheet(workbook, type);
        return fromExcel(xssfSheet, type);

    }

    /**
     * Read excel sheet and create list of objects
     * @param xssfSheet
     * @param type
     * @param <T>
     * @return
     * @throws Exception
     */
    @Override
    public <T> List<T> fromExcel (XSSFSheet xssfSheet, Class<T> type) throws Exception {
        List<FieldMapper> fieldMappers = createFieldMapper(type);

        ExcelContentLocator locator = getContentLocation(xssfSheet, type);


        findColumnNumberOfFields(fieldMappers, xssfSheet, locator);

        List<T> objects = new ArrayList<>();

        for (int i = locator.getStartRow() + 1; i <= locator.getEndRow(); i++) {
            try {
                T newInstance = type.getDeclaredConstructor().newInstance();
                System.out.println("++++++++++++++++");
                for (FieldMapper fieldMapper : fieldMappers) {
                    Object object;
                    Cell cell = xssfSheet.getRow(i).getCell(fieldMapper.getIndex());
                    System.out.println(cell.getCellTypeEnum());
                    String cellValue = dataFormatter.formatCellValue(cell);


                    Field field = fieldMapper.getField();
                    field.setAccessible(true);
                    if("java.lang.String".equalsIgnoreCase(field.getType().getName()))
                        object = cellValue;
                    else if("java.util.Date".equalsIgnoreCase(field.getType().getName())) {
                        if (cell.getCellTypeEnum() == CellType.NUMERIC)
                            object = cell.getDateCellValue();
                        else {
                            try {
                                object = defaultDateFormat.parse(cellValue);
                            } catch (ParseException e) {
                                //TODO: new exception
                                throw new Exception("Incorrect date format");

                            }
                        }

                    } else if(field.getType() == Character.TYPE)
                        object = cellValue.charAt(0);
                    else if (field.getType() == Short.TYPE)
                        object = Short.parseShort(cellValue);
                    else if (field.getType() == Integer.TYPE)
                        object = Integer.parseInt(cellValue);
                    else
                        throw new Exception("Unsupported type: " + field.getType().getName());

                    field.set(newInstance, object);

                }
                objects.add(newInstance);
            } catch (InvocationTargetException | IllegalAccessException | InstantiationException | NoSuchMethodException e) {
                System.out.println(e);
            }


        }
        return objects;
    }

    /**
     *
     * @param type
     * @param <T>
     * @return
     */
    private <T> List<FieldMapper> createFieldMapper (Class<T> type) {
        Field [] fields = type.getDeclaredFields();
        List<FieldMapper> fieldMappers = new ArrayList<>();
        for (Field field : fields) {
            String fieldName = field.getAnnotation(ExcelColumn.class) != null
                    ? field.getAnnotation(ExcelColumn.class).value() : field.getName();
            FieldMapper fieldMapper = new FieldMapper(fieldName, field);
            fieldMappers.add(fieldMapper);
        }

        return fieldMappers;
    }

    /**
     *
     * @param fieldMappers
     * @param locator
     * @throws Exception
     */
    private void findColumnNumberOfFields (List<FieldMapper> fieldMappers, XSSFSheet xssfSheet, ExcelContentLocator locator) throws Exception{
        int counter = fieldMappers.size();
        System.out.println(locator);
        int numberOfColumns = locator.getEndCol() - locator.getStartCol() + 1;
        if(numberOfColumns < counter)
            throw new Exception("Expected fields number is greater than what's in the table");

        for (int i = locator.getStartCol(); i <= locator.getEndCol(); i ++) {
            Cell cell = xssfSheet.getRow(locator.getStartRow()).getCell(i);
            for (FieldMapper fieldMapper : fieldMappers) {
                if (fieldMapper.getFieldName().equalsIgnoreCase(cell.getStringCellValue())) {
                    fieldMapper.setIndex(cell.getColumnIndex());
                    counter--;
                }

            }
            if (counter == 0)
                return;
        }
        //TODO: check if counter is greater than 0
        throw new Exception("value not found");
    }

    private <T> XSSFSheet getSheet (XSSFWorkbook xssfWorkbook, Class<T> type) throws Exception{
        ExcelSheet excelSheet = type.getAnnotation(ExcelSheet.class);
        if(excelSheet == null)
            return ExcelUtil.getExcelSheetByIndex(xssfWorkbook, 0);
        else if(StringUtils.isNotBlank(excelSheet.name()))
            return ExcelUtil.getExcelSheetByName(xssfWorkbook, excelSheet.name());
        else
            return ExcelUtil.getExcelSheetByIndex(xssfWorkbook, excelSheet.index());
    }

    private <T> String getTableName (Class<T> type) {
        return type.getAnnotation(ExcelTable.class) != null ?
                type.getAnnotation(ExcelTable.class).value() : "";
    }

    private <T> ExcelContentLocator getContentLocation (XSSFSheet xssfSheet, Class<T> type) throws Exception {
        String tableName = getTableName(type);
        if (StringUtils.isBlank(tableName))
            return new ExcelContentLocator(0, 0,
                    xssfSheet.getPhysicalNumberOfRows() - 1,
                    xssfSheet.getRow(0).getPhysicalNumberOfCells() - 1);
        List<XSSFTable> tables = xssfSheet.getTables();
        if (tables == null || tables.size() < 1)
            throw new Exception("No table present in the excel sheet");
        for (XSSFTable xssfTable : tables) {
            if (xssfTable.getName().equalsIgnoreCase(tableName))
                return getLocation(xssfTable);
        }
        throw new Exception("Table not found");
    }

    private ExcelContentLocator getLocation (XSSFTable xssfTable) {
        return new ExcelContentLocator(xssfTable.getStartRowIndex(), xssfTable.getStartColIndex(),
                xssfTable.getEndRowIndex(), xssfTable.getEndColIndex());
    }
}
