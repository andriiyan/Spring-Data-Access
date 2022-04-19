package com.github.andriiyan.spring_data_access.impl.storage;

import com.github.andriiyan.spring_data_access.api.model.Identifierable;
import com.github.andriiyan.spring_data_access.api.storage.Storage;
import com.github.andriiyan.spring_data_access.impl.dao.exception.ModelNotFoundException;
import com.github.andriiyan.spring_data_access.impl.utils.file.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

class StorageImpl<T extends Identifierable> implements Storage<T> {

    private static final Logger logger = LoggerFactory.getLogger(StorageImpl.class);

    @Nullable
    private String initializationFilePath;
    @Nullable
    private FileUtils fileUtils;
    @Nullable
    private String templateClassName;
    @NonNull
    private final Map<Long, T> mStorage = new HashMap<>();

    private volatile int lastId = 0;

    @Override
    public T save(T model) {
        synchronized (this) {
            model.setId(++lastId);
            mStorage.put(model.getId(), model);
        }
        return model;
    }

    @Override
    public boolean delete(long id) {
        synchronized (this) {
            return mStorage.remove(id) != null;
        }
    }

    @Override
    public T findById(long id) {
        synchronized (this) {
            return mStorage.get(id);
        }
    }

    @Override
    public Collection<T> findAll() {
        synchronized (this) {
            return mStorage.values();
        }
    }

    @Override
    public T update(T model) throws ModelNotFoundException {
        synchronized (this) {
            if (!mStorage.containsKey(model.getId())) {
                throw new ModelNotFoundException(model.getId(), model.getClass().getName());
            }
            mStorage.replace(model.getId(), model);
            return model;
        }
    }

    @Override
    public void clean() {
        synchronized (this) {
            mStorage.clear();
        }
    }

    public void initialize() {
        if (initializationFilePath != null && !initializationFilePath.isBlank() && fileUtils != null) {
            initialize(new File(initializationFilePath));
        }
    }

    private void initialize(File file) {
        if (fileUtils == null) return;
        logger.debug("initialization invoked with path: {}", initializationFilePath);
        try {
            final Collection<T> models = fileUtils.readFromFile(file, (Class<T>) Class.forName(templateClassName));
            for (T model : models) {
                save(model);
            }
            logger.debug("storage was initialized with {} items: {}", models.size(), models);
        } catch (ClassCastException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
            logger.error(e.toString());
        }
    }

    public void setInitializationFilePath(@Nullable String initializationFilePath) {
        this.initializationFilePath = initializationFilePath;
    }

    public void setFileUtils(@Nullable FileUtils fileUtils) {
        this.fileUtils = fileUtils;
    }

    public void setTemplateClassName(@Nullable String templateClassName) {
        this.templateClassName = templateClassName;
    }
}
