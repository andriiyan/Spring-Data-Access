package com.github.andriiyan.spring_data_access.impl.dao.exception;

public class ModelNotFoundException extends Exception {

    public ModelNotFoundException(long modelId, String className) {
        super("Model " + className + " with id=" + modelId + " not found");
    }
}
