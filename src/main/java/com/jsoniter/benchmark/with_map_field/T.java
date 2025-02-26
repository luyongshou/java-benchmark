package com.jsoniter.benchmark.with_map_field;

import io.edap.io.BufOut;
import io.edap.protobuf.*;
import io.edap.protobuf.internal.ProtoBufOut;
import io.edap.protobuf.writer.StandardProtoBufWriter;

public class T {

    public static void main(String[] args) throws EncodeException {
        TestObject testObject = TestObject.createTestObject();
        ProtoBufEncoder<TestObject> codec = ProtoBufCodecRegister.INSTANCE.getEncoder(testObject.getClass());
        ProtoBufWriter writer;
        BufOut out    = new ProtoBufOut();
        writer = new StandardProtoBufWriter(out);
        codec.encode(writer, testObject);
        byte[] bs = writer.toByteArray();
        System.out.println("length=" + bs.length);
    }
}
