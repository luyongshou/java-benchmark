package com.jsoniter.benchmark.with_1_string_field;

import com.dslplatform.json.JsonWriter;
import com.jsoniter.benchmark.All;
import com.jsoniter.benchmark.ExternalSerialization;
import org.openjdk.jmh.Main;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.BenchmarkParams;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.RunnerException;
import org.redkale.convert.json.JsonConvert;

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
    JsonConvert jsonConvert;

    @Setup(Level.Trial)
    public void benchSetup(BenchmarkParams params) throws IOException {
        testObject = TestObject.createTestObject();
        byteArrayOutputStream = new ByteArrayOutputStream();

        jsonConvert = JsonConvert.root();
        byte[] bytes = jsonConvert.convertToBytes(testObject);
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
            bh.consume(jsonConvert.convertToBytes(testObject));
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
