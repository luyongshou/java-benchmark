package com.jsoniter.benchmark.codec.map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;
import com.jsoniter.benchmark.All;
import org.openjdk.jmh.Main;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.BenchmarkParams;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.RunnerException;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/*
Benchmark           Mode  Cnt       Score       Error  Units
DeserJackson.deser  avgt    5  359868.351 ± 41680.907  ns/op
 */
@State(Scope.Thread)
public class DeserJackson {

    private ObjectMapper objectMapper;
    private TypeReference<Map<String, Object>> typeReference;
    private byte[] testJSON;

    @Setup(Level.Trial)
    public void benchSetup(BenchmarkParams params) {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new AfterburnerModule());
        typeReference = new TypeReference<Map<String, Object>>() {
        };
        testJSON = TestObject.createTestJSON();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void deser(Blackhole bh) throws IOException {
        for (int i = 0; i < 1000; i++) {
            bh.consume(objectMapper.readValue(testJSON, typeReference));
        }
    }

    public static void main(String[] args) throws IOException, RunnerException {
        All.loadJMH();
        Main.main(new String[]{
                "codec.map.DeserJackson",
                "-i", "5",
                "-wi", "5",
                "-f", "1",
        });
    }
}
