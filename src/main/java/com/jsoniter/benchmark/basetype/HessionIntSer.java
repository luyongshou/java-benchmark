package com.jsoniter.benchmark.basetype;

import com.caucho.hessian.io.Hessian2Output;
import com.caucho.hessian.io.SerializerFactory;

import com.jsoniter.benchmark.All;
import com.jsoniter.benchmark.ExternalSerialization;
import org.openjdk.jmh.Main;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.BenchmarkParams;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.RunnerException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author : luysh@yonyou.com
 * @date : 2021/10/12
 */
@State(Scope.Thread)
public class HessionIntSer {

    private String testObject = "256";
    private ByteArrayOutputStream byteArrayOutputStream;
    private Hessian2Output out;

    @Setup(Level.Trial)
    public void benchSetup(BenchmarkParams params) throws IOException {

        byteArrayOutputStream = new ByteArrayOutputStream();
        out = new Hessian2Output(byteArrayOutputStream);
        SerializerFactory serializerFactory = SerializerFactory.createDefault();
        out.setSerializerFactory(serializerFactory);
        out.writeObject(testObject);
        out.close();
        byte[] bs = byteArrayOutputStream.toByteArray();
        System.out.println("length=" + bs.length);
        System.out.println("+-----------------------------------------------+");
        System.out.println(new String(bs));
        System.out.println("+-----------------------------------------------+");

        out.writeObject(testObject);
        out.close();
        bs = byteArrayOutputStream.toByteArray();
        System.out.println("length=" + bs.length);
        System.out.println("+-----------------------------------------------+");
        System.out.println(new String(bs));
        System.out.println("+-----------------------------------------------+");
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void ser(Blackhole bh) throws IOException {
        for (int i = 0; i < 1000; i++) {
            out.reset();
            out.writeObject(testObject);
            out.close();
            bh.consume(byteArrayOutputStream);
        }
    }

    public static void main(String[] args) throws IOException, RunnerException {
        All.loadJMH();
        Main.main(new String[]{
                "HessionIntSer.ser",
                "-i", "5",
                "-wi", "5",
                "-f", "1",
        });
    }
}
