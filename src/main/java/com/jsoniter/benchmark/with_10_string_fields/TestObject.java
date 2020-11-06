package com.jsoniter.benchmark.with_10_string_fields;

import com.dslplatform.json.CompiledJson;
import com.dslplatform.json.JsonAttribute;
import com.jsoniter.annotation.JsonProperty;
import com.jsoniter.output.JsonStream;

@CompiledJson
public class TestObject {

    @JsonProperty(nullable = false)
    public String field1;
    @JsonProperty(nullable = false)
    public String field2;
    @JsonProperty(nullable = false)
    public String field3;
    @JsonProperty(nullable = false)
    public String field4;
    @JsonProperty(nullable = false)
    public String field5;
    @JsonProperty(nullable = false)
    public String field6;
    @JsonProperty(nullable = false)
    public String field7;
    @JsonProperty(nullable = false)
    public String field8;
    @JsonProperty(nullable = false)
    public String field9;
    @JsonProperty(nullable = false)
    public String field10;

    public static TestObject createTestObject() {
        TestObject testObject = new TestObject();
        testObject.field1 = "1234";
        testObject.field2 = "2234";
        testObject.field3 = "3234";
        testObject.field4 = "4234";
        testObject.field5 = "5234";
        testObject.field6 = "6234";
        testObject.field7 = "7234";
        testObject.field8 = "8234";
        testObject.field9 = "9234";
        testObject.field10 = "10234";
        return testObject;
    }

    public static byte[] createTestJSON() {
        return JsonStream.serialize(createTestObject()).getBytes();
    }
}
