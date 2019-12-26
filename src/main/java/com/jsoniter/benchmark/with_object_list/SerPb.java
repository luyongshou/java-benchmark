package com.jsoniter.benchmark.with_object_list;

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
SerPb.ser  avgt    5  328219.768 ± 2099.515  ns/op
 */
@State(Scope.Thread)
public class SerPb {

    private Pb.PbTestObject testObject;
    private ByteArrayOutputStream byteArrayOutputStream;

    @Setup(Level.Trial)
    public void benchSetup(BenchmarkParams params) throws IOException {
        testObject = Pb.PbTestObject.newBuilder()
                .addField1(Pb.PbTestObject.ElementObject.newBuilder().setField1("1234").build())
                .addField1(Pb.PbTestObject.ElementObject.newBuilder().setField1("2234").build())
                .addField1(Pb.PbTestObject.ElementObject.newBuilder().setField1("3234").build())
                .addField1(Pb.PbTestObject.ElementObject.newBuilder().setField1("4234").build())

                .addField1(Pb.PbTestObject.ElementObject.newBuilder().setField1("1234").build())
                .addField1(Pb.PbTestObject.ElementObject.newBuilder().setField1("2234").build())
                .addField1(Pb.PbTestObject.ElementObject.newBuilder().setField1("3234").build())
                .addField1(Pb.PbTestObject.ElementObject.newBuilder().setField1("4234").build())

                .addField1(Pb.PbTestObject.ElementObject.newBuilder().setField1("1234").build())
                .addField1(Pb.PbTestObject.ElementObject.newBuilder().setField1("2234").build())
                .addField1(Pb.PbTestObject.ElementObject.newBuilder().setField1("3234").build())
                .addField1(Pb.PbTestObject.ElementObject.newBuilder().setField1("4234").build())

                .addField1(Pb.PbTestObject.ElementObject.newBuilder().setField1("1234").build())
                .addField1(Pb.PbTestObject.ElementObject.newBuilder().setField1("2234").build())
                .addField1(Pb.PbTestObject.ElementObject.newBuilder().setField1("3234").build())
                .addField1(Pb.PbTestObject.ElementObject.newBuilder().setField1("4234").build())
                .build();
        byteArrayOutputStream = new ByteArrayOutputStream();
        testObject.writeTo(byteArrayOutputStream);
        System.out.println("length=" + byteArrayOutputStream.size());
        testObject.writeTo(byteArrayOutputStream);
        System.out.println("\nlenght=" + byteArrayOutputStream.size());

        System.out.println("+-----------------------------------------------+");
        System.out.println(conver2HexStr(byteArrayOutputStream.toByteArray()));
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
                "with_object_list.SerPb",
                "-i", "5",
                "-wi", "5",
                "-f", "1",
        });
    }
}
