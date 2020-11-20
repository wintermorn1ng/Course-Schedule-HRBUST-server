package com.example.demo.utils;


import sun.misc.BASE64Encoder;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class FileUtil {
    public static final String filePath = "C:\\MyProject\\MyJava\\demo\\Result\\";
    public static String saveFile(byte[] data,String fileName) throws IOException {
        String name = encoding(fileName);
        File file = new File(filePath.concat(name+".jpg"));
        OutputStream outputStream = new FileOutputStream(file);
        outputStream.write(data);
        outputStream.close();
        return name;
    }
    public static byte[] readFile(String fileName) throws IOException {
        File file = new File(filePath.concat(fileName+".jpg"));
        if (!(file.exists() && file.canRead())) {
            return  null;
        }
        FileInputStream fis = null;
        byte[] buffer = null;
        fis = new FileInputStream(file);
        buffer = new byte[fis.available()];
        fis.read(buffer);
        fis.close();
        file.delete();
        return buffer;

    }
    private static String encoding(String str){
        return new BASE64Encoder().encode(str.getBytes(StandardCharsets.UTF_8));
    }
}