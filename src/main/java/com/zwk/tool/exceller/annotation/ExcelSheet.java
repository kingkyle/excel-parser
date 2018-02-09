package com.zwk.tool.exceller.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface ExcelSheet {
    String name() default "";
    int index() default 0;
}
