package com.jsoniter.benchmark.with_long_string;

import com.dslplatform.json.CompiledJson;
import com.jsoniter.output.JsonStream;

import java.io.Serializable;

@CompiledJson
public class TestObject implements Serializable {

    public String field1;

    public static TestObject createTestObject() {
        TestObject testObject = new TestObject();
        testObject.field1 = "012345678中9012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789";
        return testObject;
    }

    public static byte[] createTestJSON() {
        return JsonStream.serialize(createTestObject()).getBytes();
    }
}
