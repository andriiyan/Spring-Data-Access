package com.github.andriiyan.spring_data_access.impl.utils.file.serializer;

import com.github.andriiyan.spring_data_access.impl.utils.JsonInstanceCreator;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class JsonSerializerTest extends IOSteamTest {

    final JsonSerializer jsonSerializer = new JsonSerializer();

    private static final String TEST_CLASS_JSON = "[{\"testInt\":123}]";

    @Before
    public void init() {
        jsonSerializer.setJsonInstanceCreatorList(
                Arrays.asList(
                        new GenericJsonInstanceCreator(TestClass[].class, TestClass.class),
                        new GenericJsonInstanceCreator(DummyObject[].class, DummyObject.class)
                )
        );
    }

    @Test
    public void serialize_deserialize_should_work_properly() {
        final List<DummyObject> dummyObjectList = Arrays.asList(
                new DummyObject(1, "1", true),
                new DummyObject(12, "test1", false),
                new DummyObject(123, "Andrii", true),
                new DummyObject(1234, "Yan", true)
        );
        final ByteArrayOutputStream outputStream = newOutputStream(1024);
        assertTrue(jsonSerializer.serialize(dummyObjectList, outputStream));
        copyToByteBuffer(outputStream.toByteArray());

        assertNotNull(byteBuffer);
        assertTrue(byteBuffer.capacity() != 0);
        assertTrue(byteBuffer.limit() != 0);

        final ByteArrayInputStream inputStream = asInputStream();
        Collection<DummyObject> decodedObjectList = jsonSerializer.deserialize(
                inputStream,
                DummyObject.class
        );
        assertNotNull(decodedObjectList);

        int index = 0;
        for (DummyObject decoded : decodedObjectList) {
            DummyObject originalObject = dummyObjectList.get(index++);
            assertEquals(originalObject.getTestInt(), decoded.getTestInt());
            assertEquals(originalObject.getTestString(), decoded.getTestString());
            assertEquals(originalObject.isTestBoolean(), decoded.isTestBoolean());
        }
    }

    @Test
    public void deserialize_fromString() {
        Collection<TestClass> testClasses = jsonSerializer.deserialize(
                new ByteArrayInputStream(TEST_CLASS_JSON.getBytes(StandardCharsets.UTF_8)),
                TestClass.class
        );
        assertNotNull(testClasses);
        assertEquals(1, testClasses.size());
        final TestClass testClass = (TestClass) testClasses.toArray()[0];
        assertNotNull(testClass);
        assertEquals(123, testClass.testInt);
    }

    @Test
    public void serialize_toString() {
        final ByteArrayOutputStream outputStream = newOutputStream(1024);
        assertTrue(jsonSerializer.serialize(Collections.singleton(new TestClass(123)), outputStream));
        copyToByteBuffer(outputStream.toByteArray());

        assertNotNull(byteBuffer);
        assertTrue(byteBuffer.capacity() != 0);
        assertTrue(byteBuffer.limit() != 0);
        assertEquals(TEST_CLASS_JSON.getBytes(StandardCharsets.UTF_8).length, byteBuffer.capacity());
        assertEquals(TEST_CLASS_JSON, new String(byteBuffer.array()));
    }

    static class TestClass {
        private int testInt;

        public TestClass(int testInt) {
            this.testInt = testInt;
        }

        public int getTestInt() {
            return testInt;
        }

        public void setTestInt(int testInt) {
            this.testInt = testInt;
        }
    }

    static class GenericJsonInstanceCreator<T> implements JsonInstanceCreator<T> {
        private final Class<T[]> arrayType;
        private final Class<T> type;

        public GenericJsonInstanceCreator(Class<T[]> arrayType, Class<T> type) {
            this.arrayType = arrayType;
            this.type = type;
        }

        @Override
        public Collection<T> createInstances(String source, Gson gson) {
            return Arrays.asList(gson.fromJson(source, arrayType));
        }

        @Override
        public Class<T> getType() {
            return type;
        }
    }
}