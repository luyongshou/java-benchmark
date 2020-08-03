package com.jsoniter.benchmark.codec.map;

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
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
public class SerJackson {

    private ObjectMapper objectMapper;
    private ByteArrayOutputStream byteArrayOutputStream;
    private Map<String, Object> testObject;

    @Setup(Level.Trial)
    public void benchSetup(BenchmarkParams params) throws IOException {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new AfterburnerModule());
        byteArrayOutputStream = new ByteArrayOutputStream();
        testObject = new HashMap<>();
        testObject.put("name", "louis");
        testObject.put("age", 41);
        testObject.put("height", 170.2);

        byteArrayOutputStream.reset();
        objectMapper.writeValue(byteArrayOutputStream, testObject);
        byte[] bs = byteArrayOutputStream.toByteArray();
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
            byteArrayOutputStream.reset();
            objectMapper.writeValue(byteArrayOutputStream, testObject);
            bh.consume(byteArrayOutputStream.toByteArray());
        }
    }

    public static void main(String[] args) throws IOException, RunnerException {
        All.loadJMH();
        Main.main(new String[]{
                "codec.map.SerJackson",
                "-i", "5",
                "-wi", "5",
                "-f", "1",
        });
    }
}