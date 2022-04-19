package com.github.andriiyan.spring_data_access.impl.utils.file.serializer;

import com.github.andriiyan.spring_data_access.impl.utils.JsonInstanceCreator;
import com.github.andriiyan.spring_data_access.impl.utils.file.Serializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Serializes/deserializes plain java objects by transforming them into the string.
 */
class JsonSerializer implements Serializer {

    private static final String FILE_EXT = ".json";
    private final Gson gson = new GsonBuilder()
            .serializeNulls()
            .create();

    private List<JsonInstanceCreator<?>> jsonInstanceCreatorList;

    @Override
    public <T> boolean serialize(Collection<T> models, OutputStream outputStream) {
        try {
            outputStream.write(gson.toJson(models).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public <T> Collection<T> deserialize(InputStream inputStream, Class<T> type) {
        final String json;
        try {
            json = new String(inputStream.readAllBytes());
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
        JsonInstanceCreator<T> jsonInstanceCreator = null;
        for (JsonInstanceCreator<?> instanceCreators : jsonInstanceCreatorList) {
            if (instanceCreators.getType() == type) {
                jsonInstanceCreator = (JsonInstanceCreator<T>) instanceCreators;
            }
        }
        if (jsonInstanceCreator == null) {
            throw new IllegalArgumentException("jsonInstanceCreator for type " + type + " is not provided. " +
                    "Make sure you have set it by using `setJsonInstanceCreatorList` method");
        }
        return jsonInstanceCreator.createInstances(json, gson);
    }

    @Override
    public String fileExtension() {
        return FILE_EXT;
    }

    public void setJsonInstanceCreatorList(List<JsonInstanceCreator<?>> jsonInstanceCreatorList) {
        this.jsonInstanceCreatorList = jsonInstanceCreatorList;
    }
}
