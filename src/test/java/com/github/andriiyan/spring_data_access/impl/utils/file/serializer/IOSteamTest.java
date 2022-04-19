package com.github.andriiyan.spring_data_access.impl.utils.file.serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

abstract class IOSteamTest {

    protected ByteBuffer byteBuffer;

    protected ByteArrayOutputStream newOutputStream(int capacity) {
        return new ByteArrayOutputStream(capacity);
    }

    protected ByteArrayInputStream asInputStream(int capacity) {
        byteBuffer = ByteBuffer.allocate(capacity);
        return asInputStream();
    }

    protected ByteArrayInputStream asInputStream() {
        return new ByteArrayInputStream(byteBuffer.array());
    }

    protected void copyToByteBuffer(byte[] bytes) {
        byteBuffer = ByteBuffer.wrap(bytes);
    }

}
