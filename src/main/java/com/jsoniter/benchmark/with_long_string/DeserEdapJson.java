package com.jsoniter.benchmark.with_long_string;

import com.jsoniter.benchmark.All;
import io.edap.json.ByteArrayJsonReader;
import io.edap.json.model.ByteArrayDataRange;
import io.edap.protobuf.ProtoBuf;
import org.junit.Test;
import org.openjdk.jmh.Main;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.BenchmarkParams;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.RunnerException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

/**
 * @author : luysh@yonyou.com
 * @date : 2019/12/25
 */
@State(Scope.Thread)
public class DeserEdapJson {

    private byte[] testJSON;
    private ByteArrayJsonReader jsonReader;

    //private TestObjectDecoder testObjectDecoder;

    private TestObjectFastDecoder testObjectDecoder;

    //private Decoder testObjectDecoder;

    @Setup(Level.Trial)
    public void benchSetup(BenchmarkParams params) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        testJSON = TestObject.createTestJSON();
        jsonReader = new ByteArrayJsonReader(testJSON);
        System.out.println(new String(testJSON, StandardCharsets.UTF_8));
        //testObjectDecoder = new TestObjectDecoder();
        testObjectDecoder = new TestObjectFastDecoder();
        //testObjectDecoder = new ReflectDecoder(TestObject.class, DataType.BYTE_ARRAY);
        System.out.println(new String(testJSON, StandardCharsets.UTF_8));
        jsonReader.reset();
        TestObject testObject = testObjectDecoder.decode(jsonReader);
        System.out.println("testObject.field1=" + testObject.field1);

        jsonReader.reset();
        jsonReader.nextPos(1);
        char c = jsonReader.firstNotSpaceChar();
        com.jsoniter.benchmark.with_1_string_field.TestObject pojo = new com.jsoniter.benchmark.with_1_string_field.TestObject();
        if (c == '}') {
            return;
        }
        ByteArrayDataRange dr = (ByteArrayDataRange)jsonReader.readKeyRange();
        System.out.println(dr.hashCode()+ ",dr.start=" + dr.start() + ",dr.end=" +  dr.end());

    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void deser(Blackhole bh) throws IOException, InvocationTargetException, InstantiationException, IllegalAccessException {
        for (int i = 0; i < 1000; i++) {
            jsonReader.reset();
            bh.consume(testObjectDecoder.decode(jsonReader));
        }
    }

    @Test
    public void test() throws IOException, InvocationTargetException, InstantiationException, IllegalAccessException {
        benchSetup(null);
        assertEquals(31415926, ProtoBuf.toObject(testJSON, TestObject.class).field1);
    }

    public static void main(String[] args) throws IOException, RunnerException {
        All.loadJMH();
        Main.main(new String[]{
                "with_long_string.DeserEdapJson",
                "-i", "5",
                "-wi", "5",
                "-f", "1",
        });
    }
}
