package com.jsoniter.benchmark.basetype;

import com.jsoniter.benchmark.All;
import io.edap.io.BufOut;
import io.edap.protobuf.EncodeException;
import io.edap.protobuf.ProtoBuf;
import io.edap.protobuf.ProtoBufWriter;
import io.edap.protobuf.ext.AnyCodec;
import io.edap.protobuf.internal.ProtoBufOut;
import io.edap.protobuf.writer.StandardProtoBufWriter;
import org.junit.Test;
import org.openjdk.jmh.Main;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.BenchmarkParams;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.RunnerException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static com.jsoniter.benchmark.All.conver2HexStr;
import static org.junit.Assert.assertEquals;

/**
 * @author : luysh@yonyou.com
 * @date : 2021/10/12
 */
@State(Scope.Thread)
public class EdapIntSer {

    ProtoBufWriter writer;
    BufOut out;
    private String testObject;
    private ByteArrayOutputStream byteArrayOutputStream;

    @Setup(Level.Trial)
    public void benchSetup(BenchmarkParams params) throws EncodeException {

        testObject = "256";
        out    = new ProtoBufOut();
        writer = new StandardProtoBufWriter(out);
        byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bs = ProtoBuf.ser(testObject);
        System.out.println("length=" + bs.length);
        System.out.println("+-----------------------------------------------+");
        System.out.println(conver2HexStr(bs));
        System.out.println("+-----------------------------------------------+");
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void ser(Blackhole bh) throws IOException, EncodeException {
        for (int i = 0; i < 1000; i++) {
            writer.reset();
            AnyCodec.encode(writer, testObject);
            bh.consume(writer.toByteArray());
        }
    }

    @Test
    public void test() throws IOException, EncodeException {
        benchSetup(null);
        assertEquals("31415926", "31415926");
    }

    public static void main(String[] args) throws IOException, RunnerException {
        All.loadJMH();
        Main.main(new String[]{
                "EdapIntSer.ser",
                "-i", "5",
                "-wi", "5",
                "-f", "1",
        });
    }
}