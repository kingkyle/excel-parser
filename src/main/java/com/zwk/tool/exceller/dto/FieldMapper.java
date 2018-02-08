package com.zwk.tool.exceller.dto;

import java.lang.reflect.Field;

public class FieldMapper {

    private int index;
    private String fieldName;
    private Field field;

    public FieldMapper(String fieldName, Field field) {
        this.fieldName = fieldName;
        this.field = field;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    @Override
    public String toString() {
        return "FieldMapper{" +
                "index=" + index +
                ", fieldName='" + fieldName + '\'' +
                ", field=" + field +
                '}';
    }
}
