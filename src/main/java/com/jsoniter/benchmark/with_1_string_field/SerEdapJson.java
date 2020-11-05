package com.jsoniter.benchmark.with_1_string_field;

import com.jsoniter.benchmark.All;
import io.edap.x.io.BufOut;
import io.edap.x.io.ByteArrayBufOut;
import io.edap.x.json.JsonWriter;
import io.edap.x.json.writer.ByteArrayJsonWriter;
import io.edap.x.protobuf.ProtoBuf;
import org.junit.Test;
import org.openjdk.jmh.Main;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.BenchmarkParams;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.RunnerException;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static com.jsoniter.benchmark.All.conver2HexStr;
import static org.junit.Assert.assertEquals;

/**
 * @author : luysh@yonyou.com
 * @date : 2019/12/25
 */
@State(Scope.Thread)
public class SerEdapJson {

    private TestObject testObject;
    ByteArrayBufOut out;
    JsonWriter jw;
    TestObjectEncoder encoder;

    @Setup(Level.Trial)
    public void benchSetup(BenchmarkParams params) {
        testObject = TestObject.createTestObject();
        out = new ByteArrayBufOut();
        jw = new ByteArrayJsonWriter(out);
        encoder = new TestObjectEncoder();
        encoder.ser(jw, testObject);
        int len = jw.getPos();
        byte[] bs = new byte[len];
        System.arraycopy(out.getWriteBuf().bs, 0, bs, 0, len);
        System.out.println("length=" + bs.length);
        System.out.println("+-----------------------------------------------+");
        System.out.println(new String(bs));
        System.out.println("+-----------------------------------------------+");
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void ser(Blackhole bh) throws IOException {
        for (int i = 0; i < 1000; i++) {
            jw.reset();
            encoder.ser(jw, testObject);
            int len = jw.getPos();
            byte[] bs = new byte[len];
            System.arraycopy(out.getWriteBuf().bs, 0, bs, 0, len);
        }
    }

    public static class TestObjectEncoder {

        static byte[] field1Data = "\"field1\":null".getBytes();
        public void ser(JsonWriter jw, TestObject object) {
            jw.writeByte((byte)'{');
            if (object.field1 == null) {
                jw.writeBytes(field1Data, 0, 13);
            } else {
                jw.writeBytes(field1Data, 0, 9);
                jw.writeString(object.field1);
            }
            jw.writeByte((byte)'}');
        }
    }

    @Test
    public void test() throws IOException {
        benchSetup(null);
        assertEquals("31415926", "31415926");
    }

    public static void main(String[] args) throws IOException, RunnerException {
        All.loadJMH();
        Main.main(new String[]{
                "with_1_string_field.SerEdapJson",
                "-i", "5",
                "-wi", "5",
                "-f", "1",
        });
    }
}
