package com.jsoniter.benchmark.codec.map;

import com.jsoniter.output.JsonStream;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author : luysh@yonyou.com
 * @date : 2020/8/7
 */
public class TestObject {

    public static  Map<String, Object> map;


    static {
        StringBuilder sb = new StringBuilder();
        //for (int i=0;i<1024;i++) {
            sb.append("louis");
        //}
//        map = new ElementObject();
//        map.field1 = "louis";
        map = new HashMap<>();
        map.put("123", 41);
        Random random = new Random(100);
        for (int i=0;i<15;i++) {
            //map.put("name" + i, i);
        }
        map.put("name", sb.toString());
        map.put("height", 170.2);
        //map.put("clazz", String.class);
    }

    public static byte[] createTestJSON() {
        return JsonStream.serialize(map).getBytes();
    }
}
