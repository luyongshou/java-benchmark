package com.jsoniter.benchmark.with_object_list;

import com.dslplatform.json.JsonWriter;
import com.jsoniter.benchmark.All;
import com.jsoniter.benchmark.ExternalSerialization;
import org.openjdk.jmh.Main;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.BenchmarkParams;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.RunnerException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

import static com.jsoniter.benchmark.All.conver2HexStr;

/*
Benchmark       Mode  Cnt       Score      Error  Units
SerDslJson.ser  avgt    5  187964.594 ± 6359.931  ns/op
 */
@State(Scope.Thread)
public class SerDslJson {

    private JsonWriter jsonWriter;
    private ByteArrayOutputStream byteArrayOutputStream;
    private TestObject testObject;

    @Setup(Level.Trial)
    public void benchSetup(BenchmarkParams params) throws UnsupportedEncodingException {
        testObject = TestObject.createTestObject();
        jsonWriter = new JsonWriter();
        byteArrayOutputStream = new ByteArrayOutputStream();

        jsonWriter.reset();
        byteArrayOutputStream.reset();
        ExternalSerialization.serialize(testObject, jsonWriter, false);
        byte[] bs = jsonWriter.toByteArray();

        System.out.println("length=" + bs.length);
        System.out.println("+-----------------------------------------------+");
        System.out.println(new String(bs, "utf-8"));
        System.out.println("+-----------------------------------------------+");
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void ser(Blackhole bh) throws IOException {
        for (int i = 0; i < 1000; i++) {
            jsonWriter.reset();
            byteArrayOutputStream.reset();
            ExternalSerialization.serialize(testObject, jsonWriter, false);
            jsonWriter.toByteArray();
            bh.consume(byteArrayOutputStream);
        }
    }

    public static void main(String[] args) throws IOException, RunnerException {
        All.loadJMH();
        Main.main(new String[]{
                "with_object_list.SerDslJson",
                "-i", "5",
                "-wi", "5",
                "-f", "1",
        });
    }
}
