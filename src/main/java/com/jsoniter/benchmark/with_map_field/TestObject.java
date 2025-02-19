package com.jsoniter.benchmark.with_map_field;

import com.jsoniter.annotation.JsonProperty;
import com.jsoniter.output.JsonStream;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class TestObject implements Serializable {
    @JsonProperty(nullable = false, collectionValueNullable = false)
    public Map<String, ElementObject> field1;

    public static TestObject createTestObject() {
        TestObject testObject = new TestObject();
        testObject.field1 = new HashMap<>();
        testObject.field1.put("value1", createElement("1"));
        testObject.field1.put("value2", createElement("2"));
        testObject.field1.put("value3", createElement("3"));
        testObject.field1.put("value4", createElement("4"));

        testObject.field1.put("value5", createElement("1"));
        testObject.field1.put("value6", createElement("2"));
        testObject.field1.put("value7", createElement("3"));
        testObject.field1.put("value8", createElement("4"));

        testObject.field1.put("value9", createElement("1"));
        testObject.field1.put("value10", createElement("2"));
        testObject.field1.put("value11", createElement("3"));
        testObject.field1.put("value12", createElement("4"));

        testObject.field1.put("value13", createElement("1"));
        testObject.field1.put("value14", createElement("2"));
        testObject.field1.put("value15", createElement("3"));
        testObject.field1.put("value16", createElement("4"));
        return testObject;
    }

    private static ElementObject createElement(String field1) {
        ElementObject elementObject = new ElementObject();
        elementObject.field1 = field1;
        return elementObject;
    }

    public static byte[] createTestJSON() {
        return JsonStream.serialize(createTestObject()).getBytes();
    }
}
