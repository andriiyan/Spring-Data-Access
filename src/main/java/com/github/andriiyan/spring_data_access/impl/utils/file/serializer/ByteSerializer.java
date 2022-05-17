package com.github.andriiyan.spring_data_access.impl.utils.file.serializer;

import com.github.andriiyan.spring_data_access.impl.utils.file.Serializer;
import org.springframework.lang.NonNull;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Serializes/deserializes plain java objects by transforming them into the stream of bytes.
 */
public class ByteSerializer implements Serializer {

    private static final long terminalUUID = 51984L;

    static class TerminalObject implements Serializable {
        private static final long serialVersionUID = 1L;
        private long mTerminalUUID;

        public TerminalObject(long mTerminalUUID) {
            this.mTerminalUUID = mTerminalUUID;
        }

        public void setmTerminalUUID(long mTerminalUUID) {
            this.mTerminalUUID = mTerminalUUID;
        }
    }

    @Override
    public <T> boolean serialize(@NonNull Collection<T> models, @NonNull OutputStream outputStream) {
        try {
            final ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            for (Object item : models) {
                objectOutputStream.writeObject(item);
            }
            objectOutputStream.writeObject(new TerminalObject(terminalUUID));
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public <T> Collection<T> deserialize(@NonNull InputStream inputStream, @NonNull Class<T> type) {
        final List<T> readObjects = new ArrayList<>();
        try {
            final ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            Object readObject;
            while ((readObject = objectInputStream.readObject()) != null) {
                if (readObject instanceof TerminalObject && ((TerminalObject) readObject).mTerminalUUID == terminalUUID) {
                    break;
                }
                readObjects.add((T) readObject);
            }
            objectInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return readObjects;
        }
        return readObjects;
    }

}
