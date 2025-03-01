package com.jsoniter.benchmark.with_map_field;

import com.jsoniter.benchmark.All;
import io.edap.io.ByteArrayBufOut;
import io.edap.json.JsonCodecRegister;
import io.edap.json.JsonEncoder;
import io.edap.json.JsonWriter;
import io.edap.json.writer.ByteArrayJsonWriter;
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
        byteArrayOutputStream = new ByteArrayOutputStream();
        out = new ByteArrayBufOut();
        jw = new ByteArrayJsonWriter(out);
        encoder = JsonCodecRegister.instance().getEncoder(TestObject.class);
        encoder.encode(jw, testObject);
        int len = jw.size();
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
            encoder.encode(jw, testObject);
            byteArrayOutputStream.reset();
            jw.toStream(byteArrayOutputStream);
            bh.consume(byteArrayOutputStream);
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
                "with_map_field.SerEdapJson",
                "-i", "5",
                "-wi", "5",
                "-f", "1",
        });
    }
}
