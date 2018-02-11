package com.zwk.tool.exceller.annotation;


import java.lang.annotation.*;

/**
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
public @interface ExcelColumn {
    String value();
}
