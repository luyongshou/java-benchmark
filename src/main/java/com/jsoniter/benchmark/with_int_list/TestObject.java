package com.jsoniter.benchmark.with_int_list;

import com.dslplatform.json.CompiledJson;
import com.jsoniter.output.JsonStream;
import io.edap.x.protobuf.annotation.ProtoField;
import io.edap.x.protobuf.wire.Field;

import java.util.Arrays;
import java.util.List;

@CompiledJson
public class TestObject {

    @ProtoField(tag=1, type= Field.Type.INT32)
    public List<Integer> field1;

    public static TestObject createTestObject() {
        TestObject testObject = new TestObject();
        testObject.field1 = Arrays.asList(
                321, 123, 132, 312, 321, 123, 132, 312,
                321, 123, 132, 312, 321, 123, 132, 312);
        return testObject;
    }

    public static byte[] createTestJSON() {
        return JsonStream.serialize(createTestObject()).getBytes();
    }
}
