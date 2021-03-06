package com.jsoniter.benchmark.with_long_string;

import com.jsoniter.benchmark.All;
import io.edap.x.protobuf.ProtoBuf;
import io.edap.x.protobuf.ProtoBufWriter;
import org.junit.Test;
import org.openjdk.jmh.Main;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.BenchmarkParams;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.RunnerException;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static io.edap.x.util.StringUtil.IS_BYTE_ARRAY;
import static io.edap.x.util.StringUtil.isLatin1;
import static org.junit.Assert.assertEquals;

/**
 * @author : luysh@yonyou.com
 * @date : 2019/12/25
 */
@State(Scope.Thread)
public class SerEdapProto {

    private TestObject testObject;

    @Setup(Level.Trial)
    public void benchSetup(BenchmarkParams params) {
        testObject = TestObject.createTestObject();
        System.out.println("length=" + testObject.field1.length());
        System.out.println("IS_BYTE_ARRAY=" + IS_BYTE_ARRAY);
        System.out.println("isLatin1(value)=" + isLatin1(testObject.field1));
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void ser(Blackhole bh) throws IOException {
        for (int i = 0; i < 1000; i++) {
            bh.consume(ProtoBuf.toByteArray(testObject));
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
                "with_long_string.SerEdapProto",
                "-i", "5",
                "-wi", "5",
                "-f", "1",
        });
    }
}
