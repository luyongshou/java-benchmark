package com.jsoniter.benchmark.with_5_string_fields;

import com.dslplatform.json.CompiledJson;
import com.jsoniter.output.JsonStream;

import java.io.Serializable;

@CompiledJson
public class TestObject implements Serializable {

    public String field1;
    public String field2;
    public String field3;
    public String field4;
    public String field5;

    public static TestObject createTestObject() {
        TestObject testObject = new TestObject();
        testObject.field1 = "1中文";
        testObject.field2 = "2中文";
        testObject.field3 = "3中文";
        testObject.field4 = "4中文";
        testObject.field5 = "5中文";
        return testObject;
    }

    public static byte[] createTestJSON() {
        return JsonStream.serialize(createTestObject()).getBytes();
    }
}
