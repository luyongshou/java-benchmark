package com.jsoniter.benchmark.codec.map;

import com.alibaba.fastjson.JSON;
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

        map = new HashMap<>();
        Map<String, Object> tmp = JSON.parseObject("{\"headers\":{\"ytsMode\":\"http_sagas\",\"ytsRootProviderId\":\"c87e2267-1001-4c70-bb2a-ab41f3b81aa3\",\"YYCtoken\":\"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhbW91bnQiOiIxMCIsImV4cCI6MTY0NjgwODAwMCwiaWF0IjoxNjQ2ODA4MDAwLCJpc3MiOiJ0T2tBY1pxS1hpd2NyWndNIiwic3ViIjoiaHR0cDovL3l0cy1odHRwLWRlbW8uZGFpbHkuYXBwLnl5dWFwLmNvbS95dHMvc2FnYXNTYXZlIiwidWlkIjoiNyJ9.ddbCQLnc4qtHwmITS4u5CD-LQLlL9jdXDd0no924jLk\",\"ytsPtxId\":\"7c55a58d-cb92-4d28-b1e5-66fbbaa94be6\",\"ytsRootServiceName\":\"http-send-demo\",\"X-spanId\":\"9d1e0bb14d4362e0\",\"ytsAction\":\"execute\",\"X-traceId\":\"9d1e0bb14d4362e0\",\"ytsGtxId\":\"20220309144436@bd6aad63-7803-44f9-932e-f9d4868c543b\",\"ytsCallService\":\"http-send-demo\",\"X-callId\":\"ee4a7f41-5a17-46b4-833d-c43c730b9acb-callid-auth-sdk\",\"ytsTxId\":\"96d26c95-6f94-4bd5-81d2-73bebb94df8e\",\"ytsEnable\":\"true\"},\"method\":\"GET\",\"url\":\"http://yts-http-demo.daily.app.yyuap.com/yts/sagasSave?uid=7&amount=10\"}");
        map.putAll(tmp);
    }

    public static byte[] createTestJSON() {
        return JsonStream.serialize(map).getBytes();
    }
}
