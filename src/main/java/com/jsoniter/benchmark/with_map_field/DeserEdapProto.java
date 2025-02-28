package com.jsoniter.benchmark.with_map_field;

import com.jsoniter.benchmark.All;
import io.edap.protobuf.ProtoBuf;
import io.edap.protobuf.ProtoBufCodecRegister;
import io.edap.protobuf.ProtoBufDecoder;
import io.edap.protobuf.reader.ByteArrayReader;
import org.junit.Test;
import org.openjdk.jmh.Main;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.BenchmarkParams;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.RunnerException;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

/**
 * @author : luysh@yonyou.com
 * @date : 2019/12/25
 */
@State(Scope.Thread)
public class DeserEdapProto {

    private byte[] testJSON;
    ProtoBufDecoder<TestObject> decoder;
    ByteArrayReader reader;

    @Setup(Level.Trial)
    public void benchSetup(BenchmarkParams params) {
        testJSON = ProtoBuf.toByteArray(TestObject.createTestObject());
        decoder = ProtoBufCodecRegister.INSTANCE.getDecoder(TestObject.class);
        reader = new ByteArrayReader(testJSON);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void deser(Blackhole bh) throws IOException {
        for (int i = 0; i < 1000; i++) {
            reader.reset();
            bh.consume(decoder.decode(reader));
        }
    }

    @Test
    public void test() throws IOException {
        benchSetup(null);
        assertEquals(31415926, ProtoBuf.toObject(testJSON, TestObject.class).field1);
    }

    public static void main(String[] args) throws IOException, RunnerException {
        All.loadJMH();
        Main.main(new String[]{
                "with_map_field.DeserEdapProto",
                "-i", "5",
                "-wi", "5",
                "-f", "1",
        });
    }
}
