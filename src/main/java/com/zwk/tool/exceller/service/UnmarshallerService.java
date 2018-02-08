package com.zwk.tool.exceller.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public interface UnmarshallerService {

    /**
     * Unmarshall excel file to expected object
     * @param fileInputStream
     * @param type
     * @param <T>
     * @return
     */
    public <T> List<T> fromExcel (FileInputStream fileInputStream, Class<T> type) throws Exception;
}
