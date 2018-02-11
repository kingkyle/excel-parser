package com.zwk.tool.exceller.util;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

public class ExcelUtil {

    public static XSSFSheet getExcelSheetByName (XSSFWorkbook workbook, String sheetName) throws Exception {
        if (workbook.getSheet(sheetName) != null)
            return workbook.getSheet(sheetName);
        else
            throw new Exception("Excel Sheet not found");
    }

    public static XSSFSheet getExcelSheetByIndex (XSSFWorkbook workbook, int index) throws Exception {
        int numberOfSheets = workbook.getNumberOfSheets();
        if (numberOfSheets > index)
            return workbook.getSheetAt(index);
        else
            throw new Exception("Sheet index " + index + " is out of range (0.." + (numberOfSheets - 1) + ")");
    }
}
