package com.jsoniter.benchmark;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

public class All {
    public static void loadJMH() {
        URLClassLoader classLoader = (URLClassLoader) All.class.getClassLoader();
        StringBuilder classpath = new StringBuilder();
        for(URL url : classLoader.getURLs())
            classpath.append(url.getPath()).append(File.pathSeparator);
        System.setProperty("java.class.path", classpath.toString());
    }

    public static String conver2HexStr(byte[] b) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            result.append(Long.toString(b[i] & 0xff, 16) + ",");
        }
        return result.toString().substring(0, result.length() - 1);
    }
}
