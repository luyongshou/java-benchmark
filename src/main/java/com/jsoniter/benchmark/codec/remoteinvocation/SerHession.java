package com.jsoniter.benchmark.codec.remoteinvocation;

import com.caucho.hessian.io.Hessian2Output;
import com.caucho.hessian.io.SerializerFactory;
import com.jsoniter.benchmark.All;
import com.jsoniter.benchmark.codec.map.TestObject;
import org.junit.Test;
import org.openjdk.jmh.Main;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.BenchmarkParams;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.RunnerException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.jsoniter.benchmark.All.conver2HexStr;
import static org.junit.Assert.assertEquals;

/**
 * @author : luysh@yonyou.com
 * @date : 2019/12/31
 */
@State(Scope.Thread)
public class SerHession {

    RemoteInvocation ri;
    private ByteArrayOutputStream byteArrayOutputStream;
    private Hessian2Output out;

    @Setup(Level.Trial)
    public void benchSetup(BenchmarkParams params) throws IOException {
        ri = RemoteInvocation.createRemoteInvocation();

        //testObject.put("height", 170.2);
        byteArrayOutputStream = new ByteArrayOutputStream();
        out = new Hessian2Output(byteArrayOutputStream);
        SerializerFactory serializerFactory = SerializerFactory.createDefault();
        out.setSerializerFactory(serializerFactory);
        out.writeObject(ri);
        out.close();
        byte[] bs = byteArrayOutputStream.toByteArray();
        System.out.println("length=" + bs.length);
//        System.out.println("+-----------------------------------------------+");
//        System.out.println(conver2HexStr(bs));
//        System.out.println(new String(bs));
//        System.out.println("+-----------------------------------------------+");

        out.writeObject(ri);
        out.close();
        bs = byteArrayOutputStream.toByteArray();
        System.out.println("length=" + bs.length);
//        System.out.println("+-----------------------------------------------+");
//        System.out.println(new String(bs));
//        System.out.println("+-----------------------------------------------+");
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void ser(Blackhole bh) throws IOException {
        for (int i = 0; i < 1000; i++) {
            out.reset();
            out.writeObject(ri);
            out.close();
            bh.consume(byteArrayOutputStream);
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
                "com.jsoniter.benchmark.codec.remoteinvocation.SerHession",
                "-i", "5",
                "-wi", "5",
                "-f", "1",
        });
    }
}
