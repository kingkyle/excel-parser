package com.zwk.tool.exceller;

import com.zwk.tool.exceller.annotation.ExcelColumnName;
import com.zwk.tool.exceller.annotation.ExcelTableName;
import com.zwk.tool.exceller.service.UnmarshallerService;
import com.zwk.tool.exceller.service.UnmarshallerServiceImpl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class Exceller {

    private Exceller () {

    }

    public static void main (String [] args) throws Exception {
        /*Method[] methods = Example.class.getDeclaredMethods();
        Field[] fields = Example.class.getDeclaredFields();
        System.out.println(Example.class.getAnnotation(ExcelTableName.class).value());

        for(Method method : methods){
            System.out.println("method = " + method.getName());
        }

        for(Field field : fields) {
            System.out.println("header = " + (field.getAnnotation(ExcelColumnName.class) != null
                    ? field.getAnnotation(ExcelColumnName.class).value() : field.getName()));
            Class type = field.getType();
        }*/

        FileInputStream file = new FileInputStream("/Users/ke.a.wang/Documents/Book1.xlsx");

        UnmarshallerService unmarshallerService = new UnmarshallerServiceImpl();
        List<Example> examples = unmarshallerService.fromExcel(file, Example.class);
        for (Example example : examples) {
            System.out.println(example.toString());
        }

    }

    /**
     *
     * @param fileInputStream
     * @param tClass
     * @param <T>
     * @return
     */
/*    public static <T> List<T> fromExcel (FileInputStream fileInputStream, Class<T> tClass) {


    }*/


}
