package com.github.andriiyan.spring_data_access.impl.utils;

import com.google.gson.Gson;
import org.springframework.lang.NonNull;

import java.util.Collection;

/**
 * Factory for creating models from the json string.
 */
public interface JsonInstanceCreator<T> {
    /**
     * Creates instance of the model from the json string.
     * @param source json
     * @param gson object for converting json string into the required type.
     * @return [Collection] of deserialized models.
     */
    @NonNull
    Collection<T> createInstances(@NonNull String source, @NonNull Gson gson);

    Class<T> getType();

}
