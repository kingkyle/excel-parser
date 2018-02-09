package com.zwk.tool.exceller.util.object;

import com.zwk.tool.exceller.annotation.ExcelColumn;
import com.zwk.tool.exceller.annotation.ExcelSheet;

import java.util.Date;
import java.util.Objects;

@ExcelSheet(name = "realSheet", index = 2)
public class Example4 {

    @ExcelColumn("aa")
    private Date a;
    @ExcelColumn("bb")
    private int b;
    private String c;

    public Example4() {
        super();
    }

    public Example4(Date a, int b, String c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public Date getA() {
        return a;
    }

    public void setA(Date a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    @Override
    public String toString() {
        return "Example{" +
                "a='" + a + '\'' +
                ", b=" + b +
                ", c='" + c + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Example4 example = (Example4) o;
        return b == example.b &&
                Objects.equals(a, example.a) &&
                Objects.equals(c, example.c);
    }

    @Override
    public int hashCode() {

        return Objects.hash(a, b, c);
    }
}
