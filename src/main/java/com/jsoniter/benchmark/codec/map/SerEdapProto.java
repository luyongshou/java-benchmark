package com.jsoniter.benchmark.codec.map;

import com.jsoniter.benchmark.All;
import io.edap.x.protobuf.EncodeException;
import io.edap.x.protobuf.ProtoBuf;
import org.junit.Test;
import org.openjdk.jmh.Main;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.BenchmarkParams;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.RunnerException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.jsoniter.benchmark.All.conver2HexStr;
import static org.junit.Assert.assertEquals;

@State(Scope.Thread)
public class SerEdapProto {

    Map testObject;

    @Setup(Level.Trial)
    public void benchSetup(BenchmarkParams params) throws EncodeException {
        testObject = TestObject.map;

        byte[] bs = ProtoBuf.ser(testObject);
        System.out.println("length=" + bs.length);
        System.out.println("+-----------------------------------------------+");
        System.out.println(conver2HexStr(bs));
        System.out.println(new String(bs));
        for (byte b : bs) {
            System.out.print((int)b + ",");
        }
        System.out.println();
        System.out.println("+-----------------------------------------------+");
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void ser(Blackhole bh) throws IOException, EncodeException {
        for (int i = 0; i < 1000; i++) {
            byte[] data = ProtoBuf.ser(testObject);
            bh.consume(data);
        }
    }

    @Test
    public void test() throws IOException {
        assertEquals("31415926", "31415926");
    }

    public static void main(String[] args) throws IOException, RunnerException {
        All.loadJMH();
        Main.main(new String[]{
                "codec.map.SerEdapProto",
                "-i", "5",
                "-wi", "5",
                "-f", "1",
        });
    }
}
