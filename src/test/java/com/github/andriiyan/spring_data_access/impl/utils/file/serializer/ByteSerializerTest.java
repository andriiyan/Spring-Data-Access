package com.github.andriiyan.spring_data_access.impl.utils.file.serializer;

import org.junit.Test;

import java.io.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

public class ByteSerializerTest extends IOSteamTest {

    final ByteSerializer byteSerializer = new ByteSerializer();

    @Test
    public void serialize_deserialize_should_work_properly() {
        final List<DummyObject> dummyObjectList = Arrays.asList(
                new DummyObject(1, "1", true),
                new DummyObject(12, "test1", false),
                new DummyObject(123, "Andrii", true),
                new DummyObject(1234, "Yan", true)
        );
        final ByteArrayOutputStream outputStream = newOutputStream(1024);
        assertTrue(byteSerializer.serialize(dummyObjectList, outputStream));
        copyToByteBuffer(outputStream.toByteArray());

        assertNotNull(byteBuffer);
        assertTrue(byteBuffer.capacity() != 0);
        assertTrue(byteBuffer.limit() != 0);

        final ByteArrayInputStream inputStream = asInputStream();
        Collection<DummyObject> decodedObjectList = byteSerializer.deserialize(inputStream, null);
        assertNotNull(decodedObjectList);

        int index = 0;
        for(DummyObject decoded : decodedObjectList) {
            DummyObject originalObject = dummyObjectList.get(index++);
            assertEquals(originalObject.getTestInt(), decoded.getTestInt());
            assertEquals(originalObject.getTestString(), decoded.getTestString());
            assertEquals(originalObject.isTestBoolean(), decoded.isTestBoolean());
        }
   }

}