package com.jsoniter.benchmark.with_1_string_field;

import io.edap.json.*;
import io.edap.json.model.ByteArrayDataRange;
import io.edap.json.model.DataRange;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class TestObjectFastDecoder implements JsonDecoder<TestObject> {

    private static final Map<DataRange, ValueSetter> FIELD_SETTTERS = new HashMap<>();

    static {
        ValueSetter<TestObject> valueSetter = new ValueSetter<TestObject>() {
            @Override
            public void set(TestObject bean, JsonReader parser) {
                bean.field1 = parser.readString();
            }
        };
        FIELD_SETTTERS.put(ByteArrayDataRange.from("field1"), valueSetter);
    }

    public static ByteArrayDataRange KEY_DATARANGE = ByteArrayDataRange.from("field1");

    public TestObjectFastDecoder() {

    }

    @Override
    public TestObject decode(JsonReader jsonReader) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        char c;
        if (jsonReader.firstNotSpaceChar() != '{') {
            return null;
        }
        jsonReader.nextPos(1);
        TestObject pojo = new TestObject();
        if (jsonReader.firstNotSpaceChar() == '}') {
            return pojo;
        }
        DataRange dr = jsonReader.readKeyRange();
        if (dr.hashCode() == 1212206434) {
            pojo.field1 = jsonReader.readString();
        }
//        int hash = jsonReader.keyHash();
//        if (hash == 1212206434) {
//            pojo.field1 = jsonReader.readString();
//        }
//        DataRange dr = jsonReader.readKeyRange();
//        ValueSetter<TestObject> setter = FIELD_SETTTERS.get(dr);
//        if (setter != null) {
//            setter.set(pojo, jsonReader);
//        } else {
//            jsonReader.skipValue();
//        }
        c = jsonReader.firstNotSpaceChar();
        while (c == ',') {
            jsonReader.nextPos(1);

//            hash = jsonReader.keyHash();
//            if (hash == 1212206434) {
//                pojo.field1 = jsonReader.readString();
//            }
//                setter = fieldSetters.get(dr);
//                if (setter != null) {
//                    setter.set(pojo, jsonReader);
//                } else {
//                    jsonReader.skipValue();
//                }
            c = jsonReader.firstNotSpaceChar();
        }
        if (c != '}') {
            throw new JsonParseException("key and value 后为不符合json字符[" + (char)c + "]");
        }

        return pojo;
    }
}
