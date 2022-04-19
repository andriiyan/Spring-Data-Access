package com.github.andriiyan.spring_data_access.api.dao;

import com.github.andriiyan.spring_data_access.api.model.Identifierable;
import com.github.andriiyan.spring_data_access.impl.dao.exception.ModelNotFoundException;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Collection;

public interface BaseDao<T extends Identifierable> {

    /**
     * Saves model into the storage.
     *
     * @param model model that should be saved in the storage.
     * @return model that was saved into the storage.
     */
    @NonNull
    T save(@NonNull T model);

    /**
     * Updates model in the storage.
     *
     * @param model new model that should be saved in the storage.
     * @return model that was saved into the storage, if model was not persists before - returning null.
     */
    @Nullable
    T update(@NonNull T model) throws ModelNotFoundException;

    /**
     * Finds the model, which contains particular id.
     *
     * @param id id which model should have
     * @return model that has particular id, if such model is not persists - null.
     */
    @Nullable
    T findById(long id);

    /**
     * @return all models that are contained in the storage.
     */
    @NonNull
    Collection<T> findAll();

    /**
     * Deletes model, which has particular identifier, from the storage.
     *
     * @param id id of the model
     * @return true if model was successfully deleted, otherwise - false.
     */
    boolean delete(long id);

    /**
     * Removes all records from the storage.
     */
    void clean();

}
