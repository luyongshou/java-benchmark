package com.jsoniter.benchmark.with_10_string_fields;

import com.jsoniter.benchmark.All;
import io.edap.x.io.ByteArrayBufOut;
import io.edap.x.json.JsonCodecRegister;
import io.edap.x.json.JsonEncoder;
import io.edap.x.json.JsonWriter;
import io.edap.x.json.writer.ByteArrayJsonWriter;
import org.junit.Test;
import org.openjdk.jmh.Main;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.BenchmarkParams;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.RunnerException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

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
    JsonEncoder encoder;
    private ByteArrayOutputStream byteArrayOutputStream;

    @Setup(Level.Trial)
    public void benchSetup(BenchmarkParams params) {
        testObject = TestObject.createTestObject();
        out = new ByteArrayBufOut();
        jw = new ByteArrayJsonWriter(out);
        encoder = JsonCodecRegister.INSTANCE.getEncoder(TestObject.class);
        byteArrayOutputStream = new ByteArrayOutputStream();
        //encoder = new TestObjectEncoder();
        encoder.encode(jw, testObject);
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
            byteArrayOutputStream.reset();
            encoder.encode(jw, testObject);
//            jw.toStream(byteArrayOutputStream);
//            bh.consume(byteArrayOutputStream);
            int len = jw.getPos();
            byte[] bs = new byte[len];
            System.arraycopy(out.getWriteBuf().bs, 0, bs, 0, len);

        }
    }

    @Test
    public void test() throws IOException {
        benchSetup(null);
        assertEquals("31415926", "31415926");
    }

    public static class TestObjectEncoder  {
        private static final byte[] KBS_FIELD1 = ",\"field1\":null".getBytes();
        private static final byte[] KBS_FIELD10 = ",\"field10\":null".getBytes();
        private static final byte[] KBS_FIELD2 = ",\"field2\":null".getBytes();
        private static final byte[] KBS_FIELD3 = ",\"field3\":null".getBytes();
        private static final byte[] KBS_FIELD4 = ",\"field4\":null".getBytes();
        private static final byte[] KBS_FIELD5 = ",\"field5\":null".getBytes();
        private static final byte[] KBS_FIELD6 = ",\"field6\":null".getBytes();
        private static final byte[] KBS_FIELD7 = ",\"field7\":null".getBytes();
        private static final byte[] KBS_FIELD8 = ",\"field8\":null".getBytes();
        private static final byte[] KBS_FIELD9 = ",\"field9\":null".getBytes();

        public TestObjectEncoder() {
        }

        public void encode(ByteArrayJsonWriter var1, TestObject var2) {
            var1.writeByte((byte)'{');
            if (var2.field1 != null) {
                var1.writeBytes(KBS_FIELD1, 1, 9);
                //var1.writeAscii("\"field1\":null", 9);
                var1.writeString(var2.field1);
            }

            if (var2.field10 != null) {
                var1.writeBytes(KBS_FIELD10, 0, 11);
                //var1.writeAscii("\"field1\":null", 9);
                var1.writeString(var2.field10);
            }

            if (var2.field2 != null) {
                var1.writeBytes(KBS_FIELD2, 0, 10);
                //var1.writeAscii("\"field1\":null", 9);
                var1.writeString(var2.field2);
            }

            if (var2.field3 != null) {
                var1.writeBytes(KBS_FIELD3, 0, 10);
                //var1.writeAscii("\"field1\":null", 9);
                var1.writeString(var2.field3);
            }

            if (var2.field4 != null) {
                var1.writeBytes(KBS_FIELD4, 0, 10);
                //var1.writeAscii("\"field1\":null", 9);
                var1.writeString(var2.field4);
            }

            if (var2.field5 != null) {
                var1.writeBytes(KBS_FIELD5, 0, 10);
                //var1.writeAscii("\"field1\":null", 9);
                var1.writeString(var2.field5);
            }

            if (var2.field6 != null) {
                var1.writeBytes(KBS_FIELD6, 0, 10);
                //var1.writeAscii("\"field1\":null", 9);
                var1.writeString(var2.field6);
            }

            if (var2.field7 != null) {
                var1.writeBytes(KBS_FIELD7, 0, 10);
                //var1.writeAscii("\"field1\":null", 9);
                var1.writeString(var2.field7);
            }

            if (var2.field8 != null) {
                var1.writeBytes(KBS_FIELD8, 0, 10);
                //var1.writeAscii("\"field1\":null", 9);
                var1.writeString(var2.field8);
            }

            if (var2.field9 != null) {
                var1.writeBytes(KBS_FIELD9, 0, 10);
                //var1.writeAscii("\"field1\":null", 9);
                var1.writeString(var2.field9);
            }

                var1.writeByte((byte)125);


        }
    }
    public static void main(String[] args) throws IOException, RunnerException {
        All.loadJMH();
        Main.main(new String[]{
                "with_10_string_fields.SerEdapJson",
                "-i", "5",
                "-wi", "5",
                "-f", "1",
        });
    }
}
