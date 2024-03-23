package com.jsoniter.benchmark.basetype;

import com.dslplatform.json.DslJson;
import com.dslplatform.json.JsonWriter;
import com.dslplatform.json.NumberConverter;
import com.jsoniter.benchmark.All;
import com.jsoniter.benchmark.ExternalSerialization;
import com.jsoniter.benchmark.with_1_string_field.TestObject;
import org.openjdk.jmh.Main;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.BenchmarkParams;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.RunnerException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/*
Benchmark       Mode  Cnt      Score     Error  Units
SerDslJson.ser  avgt    5  47890.664 ± 491.719  ns/op
 */
@State(Scope.Thread)
public class SerDslJson {

    private JsonWriter jsonWriter;
    private ByteArrayOutputStream byteArrayOutputStream;
    private String testObject;
    DslJson dslJson = new DslJson();

    @Setup(Level.Trial)
    public void benchSetup(BenchmarkParams params) throws IOException {
        testObject = "256";
        jsonWriter = new JsonWriter();
        byteArrayOutputStream = new ByteArrayOutputStream();
        dslJson.serialize(jsonWriter, testObject);
        jsonWriter.toStream(byteArrayOutputStream);
        System.out.println("length=" + byteArrayOutputStream.size());
        System.out.println("+-----------------------------------------------+");
        System.out.println(new String(byteArrayOutputStream.toByteArray()));
        System.out.println("+-----------------------------------------------+");
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void ser(Blackhole bh) throws IOException {
        for (int i = 0; i < 1000; i++) {
            jsonWriter.reset();
            //byteArrayOutputStream.reset();
            //NumberConverter.serialize(testObject, jsonWriter);
            dslJson.serialize(jsonWriter, testObject);
            bh.consume(jsonWriter.toByteArray());
        }
    }

    public static void main(String[] args) throws IOException, RunnerException {
        All.loadJMH();
        Main.main(new String[]{
                "basetype.SerDslJson",
                "-i", "5",
                "-wi", "5",
                "-f", "1",
        });
    }
}
