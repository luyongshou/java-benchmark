package com.jsoniter.benchmark.codec.map;

import com.jsoniter.JsonIterator;
import com.jsoniter.benchmark.All;
import com.jsoniter.output.EncodingMode;
import com.jsoniter.output.JsonStream;
import com.jsoniter.spi.DecodingMode;
import com.jsoniter.spi.TypeLiteral;
import org.junit.Test;
import org.openjdk.jmh.Main;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.BenchmarkParams;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.RunnerException;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

/*
Benchmark            Mode  Cnt       Score      Error  Units
DeserJsoniter.deser  avgt    5  136255.999 ± 6523.651  ns/op


Benchmark            Mode  Cnt       Score      Error  Units
DeserJsoniter.deser  avgt    5  149535.679 ± 4966.105  ns/op
 */
@State(Scope.Thread)
public class DeserJsoniter {

    private byte[] testJSON;
    private JsonIterator iter;
    private TypeLiteral<Map<String, Object>> typeLiteral;

    @Setup(Level.Trial)
    public void benchSetup(BenchmarkParams params) {
        JsonStream.setMode(EncodingMode.DYNAMIC_MODE);
        JsonIterator.setMode(DecodingMode.DYNAMIC_MODE_AND_MATCH_FIELD_STRICTLY);
        testJSON = TestObject.createTestJSON();
        iter = new JsonIterator();
        typeLiteral = TypeLiteral.create(Map.class);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void deser(Blackhole bh) throws IOException {
        for (int i = 0; i < 1000; i++) {
            iter.reset(testJSON);
            bh.consume(iter.read(typeLiteral));
        }
    }

    @Test
    public void test() throws IOException {
        benchSetup(null);
        iter.reset(testJSON);
        //assertEquals("", iter.read(typeLiteral).field1);
    }

    public static void main(String[] args) throws IOException, RunnerException {
        All.loadJMH();
        Main.main(new String[]{
                "codec.map.DeserJsoniter",
                "-i", "5",
                "-wi", "5",
                "-f", "1",
        });
    }
}
