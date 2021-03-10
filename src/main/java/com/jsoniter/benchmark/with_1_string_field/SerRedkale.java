package com.jsoniter.benchmark.with_1_string_field;

import com.jsoniter.benchmark.All;
import com.jsoniter.benchmark.ExternalSerialization;
import javassist.bytecode.ByteArray;
import org.openjdk.jmh.Main;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.BenchmarkParams;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.RunnerException;
import org.redkale.convert.Encodeable;
import org.redkale.convert.json.JsonBytesWriter;
import org.redkale.convert.json.JsonConvert;
import org.redkale.convert.json.JsonFactory;
import org.redkale.convert.json.JsonWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author : luysh@yonyou.com
 * @date : 2021/3/9
 */
@State(Scope.Thread)
public class SerRedkale {

    private TestObject testObject;
    private ByteArrayOutputStream byteArrayOutputStream;

    ByteArray array ;
    JsonBytesWriter writer;
    Encodeable<JsonWriter, Object> encoder;

    @Setup(Level.Trial)
    public void benchSetup(BenchmarkParams params) throws IOException {
        testObject = TestObject.createTestObject();
        byteArrayOutputStream = new ByteArrayOutputStream();

        array = new ByteArray();
        writer = new JsonBytesWriter();
        encoder = JsonFactory.root().loadEncoder(testObject.getClass());
        encoder.convertTo(writer, testObject);
        byte[] bytes = writer.toBytes();
        System.out.println("length=" + bytes.length);
        System.out.println("+-----------------------------------------------+");
        System.out.println(new String(bytes));
        System.out.println("+-----------------------------------------------+");
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void ser(Blackhole bh) throws IOException {
        for (int i = 0; i < 1000; i++) {
            writer.clear();
            encoder.convertTo(writer,testObject);
            bh.consume(writer.toBytes());
        }
    }

    public static void main(String[] args) throws IOException, RunnerException {
        All.loadJMH();
        Main.main(new String[]{
                "with_1_string_field.SerRedkale",
                "-i", "5",
                "-wi", "5",
                "-f", "1",
        });
    }
}
