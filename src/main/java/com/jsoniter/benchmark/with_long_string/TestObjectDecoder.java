package com.jsoniter.benchmark.with_long_string;

import io.edap.json.*;
import io.edap.json.model.ByteArrayDataRange;
import io.edap.json.model.DataRange;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class TestObjectDecoder implements JsonDecoder<TestObject> {

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

    public TestObjectDecoder() {

    }

    @Override
    public TestObject decode(JsonReader jsonReader) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        NodeType nodeType = jsonReader.readStart();
        if (nodeType != NodeType.OBJECT) {
            return null;
        }
        jsonReader.nextPos(1);
        char c = jsonReader.firstNotSpaceChar();
        TestObject pojo = new TestObject();
        if (c == '}') {
            return pojo;
        }
        Map<DataRange, ValueSetter> fieldSetters = FIELD_SETTTERS;
        DataRange dr = jsonReader.readKeyRange();
        ValueSetter setter = fieldSetters.get(dr);
        if (setter != null) {
            setter.set(pojo, jsonReader);
        } else {
            jsonReader.skipValue();
        }
        c = jsonReader.firstNotSpaceChar();
        while (true) {
            if (c == '}') {
                break;
            } else if (c == ',') {
                jsonReader.nextPos(1);
                dr = jsonReader.readKeyRange();
                setter = fieldSetters.get(dr);
                if (setter != null) {
                    setter.set(pojo, jsonReader);
                } else {
                    jsonReader.skipValue();
                }
                c = jsonReader.firstNotSpaceChar();
            } else {
                throw new JsonParseException("key and value 后为不符合json字符[" + (char)c + "]");
            }
        }

        return pojo;
    }
}
