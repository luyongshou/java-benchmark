package com.jsoniter.benchmark.with_5_string_fields;

import com.google.protobuf.InvalidProtocolBufferException;
import com.jsoniter.benchmark.All;
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

/*
Benchmark  Mode  Cnt       Score      Error  Units
SerPb.ser  avgt    5  127933.179 ± 3502.340  ns/op
 */
@State(Scope.Thread)
public class SerPb {

    private PbTestObect.PbTestObject testObject;
    private ByteArrayOutputStream byteArrayOutputStream;

    @Setup(Level.Trial)
    public void benchSetup(BenchmarkParams params) throws IOException {
        testObject = PbTestObect.PbTestObject.newBuilder()
                .setField1("1中文")
                .setField2("2中文")
                .setField3("3中文")
                .setField4("4中文")
                .setField5("5中文")
                .build();
        byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.reset();
        testObject.writeTo(byteArrayOutputStream);
        byte[] bs = byteArrayOutputStream.toByteArray();
        System.out.println("length=" + bs.length);
        System.out.println("+-----------------------------------------------+");
        System.out.println(conver2HexStr(bs));
        System.out.println("+-----------------------------------------------+");
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void ser(Blackhole bh) throws IOException {
        for (int i = 0; i < 1000; i++) {
            byteArrayOutputStream.reset();
            testObject.writeTo(byteArrayOutputStream);
            bh.consume(byteArrayOutputStream);
        }
    }

    @Test
    public void test() throws InvalidProtocolBufferException {
//        benchSetup(null);
//        Pb.PbTestObject parsed = Pb.PbTestObject.parseFrom(testData);
//        System.out.println(parsed.getField1());
    }

    public static void main(String[] args) throws IOException, RunnerException {
        All.loadJMH();
        Main.main(new String[]{
                "with_5_string_fields.SerPb",
                "-i", "5",
                "-wi", "5",
                "-f", "1",
        });
    }
}
