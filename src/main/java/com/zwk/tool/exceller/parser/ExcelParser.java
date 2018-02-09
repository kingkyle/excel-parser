package com.zwk.tool.exceller.parser;

import com.zwk.tool.exceller.service.UnmarshallerService;
import com.zwk.tool.exceller.service.UnmarshallerServiceImpl;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

public class ExcelParser {

    private ExcelParser() {

    }


    public static <T> List<T> fromExcel (InputStream inputStream, Class<T> tClass) throws Exception{

        UnmarshallerService unmarshallerService = new UnmarshallerServiceImpl();
        return unmarshallerService.fromExcel(inputStream, tClass);

    }


}
