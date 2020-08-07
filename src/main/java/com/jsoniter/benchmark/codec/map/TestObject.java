package com.jsoniter.benchmark.codec.map;

import com.jsoniter.output.JsonStream;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : luysh@yonyou.com
 * @date : 2020/8/7
 */
public class TestObject {

    public static Map<String, Object> map;

    static {
        map = new HashMap<>();
        map.put("123", 41);
        map.put("name", "louis");
        map.put("height", 170.2);
    }

    public static byte[] createTestJSON() {
        return JsonStream.serialize(map).getBytes();
    }
}
