package com.jsoniter.benchmark.basetype;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;
import com.jsoniter.benchmark.All;
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
Benchmark       Mode  Cnt       Score      Error  Units
SerJackson.ser  avgt    5  117604.479 ± 4629.643  ns/op
 */
@State(Scope.Thread)
public class SerJackson {

    private ObjectMapper objectMapper;
    private ByteArrayOutputStream byteArrayOutputStream;
    private int testObject;

    @Setup(Level.Trial)
    public void benchSetup(BenchmarkParams params) {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new AfterburnerModule());
        byteArrayOutputStream = new ByteArrayOutputStream();
        testObject = 256;
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void ser(Blackhole bh) throws IOException {
        for (int i = 0; i < 1000; i++) {
            byteArrayOutputStream.reset();
            objectMapper.writeValue(byteArrayOutputStream, testObject);
            bh.consume(byteArrayOutputStream);
        }
    }

    public static void main(String[] args) throws IOException, RunnerException {
        All.loadJMH();
        Main.main(new String[]{
                "basetype.SerJackson",
                "-i", "5",
                "-wi", "5",
                "-f", "1",
        });
    }
}
