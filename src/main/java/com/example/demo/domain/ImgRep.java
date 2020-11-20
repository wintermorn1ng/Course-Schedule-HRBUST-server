package com.example.demo.domain;

import java.util.HashMap;
import java.util.Map;

public class ImgRep {
    private static Map<String,byte[]> imgMap = new HashMap<>();
    public static byte[] get(String key){
        return imgMap.get(key);
    }
    public static void put(String key,byte[] val){
        if(imgMap.size()>100)imgMap.clear();
        imgMap.put(key,val);
    }
}
