package com.zwk.tool.exceller.util;

import java.io.FileInputStream;
import java.io.InputStream;

public class Fixture {

    public static InputStream getFile (String classPath) {
        return Fixture.class.getClassLoader().getResourceAsStream(classPath);
    }
}
