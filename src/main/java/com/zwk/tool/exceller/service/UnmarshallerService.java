package com.zwk.tool.exceller.service;

import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.io.InputStream;
import java.util.List;

public interface UnmarshallerService {

    /**
     * Unmarshall excel file to expected object
     * @param inputStream
     * @param type
     * @param <T>
     * @return
     */
    public <T> List<T> fromExcel (InputStream inputStream, Class<T> type) throws Exception;

    public <T> List<T> fromExcel (XSSFSheet xssfSheet, Class<T> type) throws Exception;
}
