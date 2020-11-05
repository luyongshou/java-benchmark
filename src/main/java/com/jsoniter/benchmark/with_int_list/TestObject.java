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
                1, 2, 3, 4, 5, 6, 7, 8,
                9, 10, 11, 12, 13, 14, 15, 16);
        return testObject;
    }

    public static byte[] createTestJSON() {
        return JsonStream.serialize(createTestObject()).getBytes();
    }
}
