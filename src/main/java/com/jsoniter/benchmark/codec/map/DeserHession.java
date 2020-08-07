package com.jsoniter.benchmark.codec.map;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.caucho.hessian.io.SerializerFactory;
import com.jsoniter.benchmark.All;
import org.junit.Test;
import org.openjdk.jmh.Main;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.BenchmarkParams;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.RunnerException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/*
Benchmark            Mode  Cnt      Score      Error  Units
DeserJsoniter.deser  avgt    5  29887.361 ± 1123.891  ns/op
 */
@State(Scope.Thread)
public class DeserHession {

    private byte[] testJSON;
    Hessian2Input in;
    ByteArrayInputStream input;

    @Setup(Level.Trial)
    public void benchSetup(BenchmarkParams params) throws IOException {
        Map<String, Object> testObject = TestObject.map;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Hessian2Output out = new Hessian2Output(byteArrayOutputStream);
        SerializerFactory serializerFactory = SerializerFactory.createDefault();
        out.setSerializerFactory(serializerFactory);
        out.writeObject(testObject);
        out.close();
        testJSON = byteArrayOutputStream.toByteArray();
        input = new ByteArrayInputStream(testJSON);
        in = new Hessian2Input(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
        in.setSerializerFactory(serializerFactory);
        System.out.println("+-----------------------------------------------+");
        System.out.println(in.readObject());
        System.out.println("+-----------------------------------------------+");
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void deser(Blackhole bh) throws IOException {
        for (int i = 0; i < 1000; i++) {
            input.reset();
            in.init(input);
            in = new Hessian2Input(input);
            bh.consume(in.readObject());
        }
    }

    @Test
    public void test() throws IOException {
        benchSetup(null);

    }

    public static void main(String[] args) throws IOException, RunnerException {
        All.loadJMH();
        Main.main(new String[]{
                "codec.map.DeserHession",
                "-i", "5",
                "-wi", "5",
                "-f", "1",
        });
    }
}
