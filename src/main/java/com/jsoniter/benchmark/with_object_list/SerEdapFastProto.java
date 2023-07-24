package com.jsoniter.benchmark.with_object_list;

import com.jsoniter.benchmark.All;
import io.edap.io.BufOut;
import io.edap.protobuf.*;
import io.edap.protobuf.internal.ProtoBufOut;
import io.edap.protobuf.model.ProtoBufOption;
import io.edap.protobuf.writer.FastProtoBufWriter;
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
 * @date : 2019/12/25
 */
@State(Scope.Thread)
public class SerEdapFastProto {

    private TestObject testObject;
    ProtoBufEncoder codec;
    ProtoBufWriter writer;
    BufOut out;
    private ByteArrayOutputStream byteArrayOutputStream;

    @Setup(Level.Trial)
    public void benchSetup(BenchmarkParams params) throws EncodeException, IOException {
        testObject = TestObject.createTestObject();
        ProtoBufOption option = new ProtoBufOption();
        byteArrayOutputStream = new ByteArrayOutputStream();

        option.setCodecType(ProtoBuf.CodecType.FAST);
        codec = ProtoBufCodecRegister.INSTANCE.getEncoder(testObject.getClass(), option);
        out    = new ProtoBufOut();
        writer = new FastProtoBufWriter(out);
        writer.reset();
        byteArrayOutputStream.reset();
        codec.encode(writer, testObject);
        writer.toStream(byteArrayOutputStream);
        byte[] bs = byteArrayOutputStream.toByteArray();
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
            byteArrayOutputStream.reset();
            codec.encode(writer, testObject);
            writer.toStream(byteArrayOutputStream);
            bh.consume(byteArrayOutputStream);
        }
    }

    @Test
    public void test() throws IOException {
        assertEquals("31415926", "31415926");
    }

    public static void main(String[] args) throws IOException, RunnerException {
        All.loadJMH();
        Main.main(new String[]{
                "with_object_list.SerEdapFastProto",
                "-i", "5",
                "-wi", "5",
                "-f", "1",
        });
    }
}
