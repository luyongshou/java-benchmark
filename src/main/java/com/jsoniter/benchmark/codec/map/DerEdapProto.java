package com.jsoniter.benchmark.codec.map;

import com.alibaba.fastjson.JSON;
import com.jsoniter.benchmark.All;
import io.edap.x.protobuf.EncodeException;
import io.edap.x.protobuf.ProtoBuf;
import io.edap.x.protobuf.ProtoBufException;
import org.junit.Test;
import org.openjdk.jmh.Main;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.BenchmarkParams;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.RunnerException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.jsoniter.benchmark.All.conver2HexStr;
import static org.junit.Assert.assertEquals;

/**
 * @author : luysh@yonyou.com
 * @date : 2020/8/7
 */
@State(Scope.Thread)
public class DerEdapProto {

    private byte[] data;

    @Setup(Level.Trial)
    public void benchSetup(BenchmarkParams params) throws EncodeException, ProtoBufException {
        Map testObject = TestObject.map;

        data = ProtoBuf.ser(testObject);
        System.out.println("length=" + data.length);
        System.out.println("+-----------------------------------------------+");
        System.out.println(conver2HexStr(data));
        System.out.println(new String(data));
        for (byte b : data) {
            System.out.print((int)b + ",");
        }
        Map derData = (Map)ProtoBuf.der(data);
        System.out.println();
        System.out.println(derData);
        System.out.println("+-----------------------------------------------+");
        //System.out.println((derData.field1).length());
        System.out.println(JSON.toJSON(derData));
        //System.out.println((derData.get("name")).equals((String)TestObject.map.get("name")));
        System.out.println("+-----------------------------------------------+");
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void ser(Blackhole bh) throws IOException, ProtoBufException {
        for (int i = 0; i < 1000; i++) {
            bh.consume(ProtoBuf.der(data));
        }
    }

    @Test
    public void test() throws IOException {
        assertEquals("31415926", "31415926");
    }

    public static void main(String[] args) throws IOException, RunnerException {
        All.loadJMH();
        Main.main(new String[]{
                "codec.map.DerEdapProto",
                "-i", "5",
                "-wi", "5",
                "-f", "1",
        });
    }
}
