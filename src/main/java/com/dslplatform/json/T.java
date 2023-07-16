package com.dslplatform.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class T {

    public static void main(String[] args) {
        String json = "{\"name\":\"louis\"";
        JSONObject jo = JSON.parseObject(json);
    }
}
