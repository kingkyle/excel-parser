package com.zwk.tool.exceller.service;

import com.zwk.tool.exceller.annotation.ExcelColumnName;
import com.zwk.tool.exceller.dto.FieldMapper;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class UnmarshallerServiceImpl implements UnmarshallerService {

    @Override
    public <T> List<T> fromExcel (FileInputStream fileInputStream, Class<T> type) throws Exception {
        List<T> objects = new ArrayList<>();
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        XSSFSheet xssfSheet = workbook.getSheetAt(0);
        Iterator<Cell> cells = xssfSheet.getRow(0).cellIterator();
        List<FieldMapper> fieldMappers = new ArrayList<>();
        for (Field field : type.getDeclaredFields()) {
            String fieldName = field.getAnnotation(ExcelColumnName.class) != null
                    ? field.getAnnotation(ExcelColumnName.class).value() : field.getName();
            FieldMapper fieldMapper = new FieldMapper(fieldName, field);
            fieldMappers.add(fieldMapper);
        }
        int counter = fieldMappers.size();
        System.out.println("count: " + counter);
        Iterator<Cell> cellIterator = xssfSheet.getRow(0).cellIterator();
        while (cellIterator.hasNext() && counter > 0) {
            Cell cell = cellIterator.next();
            for (FieldMapper fieldMapper : fieldMappers) {
                if (fieldMapper.getFieldName().equalsIgnoreCase(cell.getStringCellValue())) {
                    fieldMapper.setIndex(cell.getColumnIndex());
                    counter--;
                }

            }
        }

        //TODO: check if counter is greater than 0
        for (int i = 1; i < xssfSheet.getPhysicalNumberOfRows(); i++) {
            try {
                T newInstance = type.getDeclaredConstructor().newInstance();
                System.out.println("++++++++++++++++");
                for (FieldMapper fieldMapper : fieldMappers) {
                    Cell cell = xssfSheet.getRow(i).getCell(fieldMapper.getIndex());
                    System.out.println(cell.getCellTypeEnum());
                    fieldMapper.getField().setAccessible(true);
                    fieldMapper.getField().set(newInstance, cell.getStringCellValue());

                    Date date = new Date(123);
                }
                objects.add(newInstance);
            } catch (InvocationTargetException | IllegalAccessException | InstantiationException | NoSuchMethodException e) {
                System.out.println(e);
            }


        }
        return objects;
    }
}
