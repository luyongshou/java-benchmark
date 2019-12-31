package com.jsoniter.benchmark.with_int;

import com.dslplatform.json.CompiledJson;
import com.jsoniter.output.JsonStream;

import java.io.Serializable;

@CompiledJson
public class TestObject implements Serializable {

    public int field1;

    public static TestObject createTestObject() {
        TestObject testObject = new TestObject();
        testObject.field1 = 100;
        return testObject;
    }

    public static byte[] createTestJSON() {
        return JsonStream.serialize(createTestObject()).getBytes();
    }
}
