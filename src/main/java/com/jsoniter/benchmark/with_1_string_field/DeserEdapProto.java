package com.jsoniter.benchmark.with_1_string_field;

import com.jsoniter.benchmark.All;
import io.edap.x.protobuf.*;
import io.edap.x.protobuf.reader.ByteArrayReader;
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

    private ProtoBufReader reader;

    private ProtoBufDecoder<TestObject> decoder = ProtoBufCodecRegister.INSTANCE.getDecoder(TestObject.class);

    @Setup(Level.Trial)
    public void benchSetup(BenchmarkParams params) {
        testJSON = ProtoBuf.toByteArray(TestObject.createTestObject());
        reader = new ByteArrayReader(testJSON);

    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void deser(Blackhole bh) throws IOException, ProtoBufException {
        for (int i = 0; i < 1000; i++) {
//            reader.reset();
//            decoder.decode(reader);
//            bh.consume(decoder.decode(reader));
            bh.consume(ProtoBuf.toObject(testJSON, TestObject.class));
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
                "with_1_string_field.DeserEdapProto",
                "-i", "5",
                "-wi", "5",
                "-f", "1",
        });
    }
}
