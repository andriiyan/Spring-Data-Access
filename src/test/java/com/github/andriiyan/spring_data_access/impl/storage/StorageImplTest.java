package com.github.andriiyan.spring_data_access.impl.storage;

import com.github.andriiyan.spring_data_access.api.model.User;
import com.github.andriiyan.spring_data_access.impl.TestModelsFactory;
import com.github.andriiyan.spring_data_access.impl.dao.exception.ModelNotFoundException;
import com.github.andriiyan.spring_data_access.impl.model.UserEntity;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class StorageImplTest {

    private final StorageImpl<User> storage = new StorageImpl<>();

    @Before
    public void before() {
        storage.clean();
    }

    @Test
    public void save_should_set_and_increment_id() {
        User savingUser = new UserEntity(0, "test", "test");
        User savedUser = storage.save(savingUser);

        Assert.assertEquals(savingUser.getEmail(), savedUser.getEmail());
        Assert.assertEquals(savingUser.getName(), savedUser.getName());
        Assert.assertEquals(1, savedUser.getId());

        savedUser = storage.save(savingUser);

        Assert.assertEquals(savingUser.getEmail(), savedUser.getEmail());
        Assert.assertEquals(savingUser.getName(), savedUser.getName());
        Assert.assertEquals(2, savedUser.getId());
    }

    @Test
    public void delete_should_return_false_if_model_is_not_stored() {
        Assert.assertFalse(storage.delete(101));
    }

    @Test
    public void delete_should_return_true_if_model_is_stored_and_delete_it() {
        User savingUser = new UserEntity(0, "test", "test");
        User savedUser = storage.save(savingUser);

        Assert.assertEquals(savedUser, storage.findById(savedUser.getId()));
        Assert.assertTrue(storage.delete(savedUser.getId()));
        Assert.assertNull(storage.findById(savedUser.getId()));
    }

    @Test
    public void findAll_should_return_all_values() {
        List<User> users = TestModelsFactory.generateUsers(10);
        List<User> savedUsers = new ArrayList<>();
        for (User user : users) {
            savedUsers.add(storage.save(user));
        }
        Assert.assertEquals(savedUsers.size(), storage.findAll().size());
        int index = 0;
        for (User user : storage.findAll()) {
            Assert.assertEquals(savedUsers.get(index++), user);
        }
    }

    @Test(expected = ModelNotFoundException.class)
    public void update_should_throw_an_exception_if_model_is_not_stored() throws ModelNotFoundException {
        User user = TestModelsFactory.generateSingleUser();
        storage.update(user);
    }

    @Test
    public void update_should_properly_update_model() throws ModelNotFoundException {
        User user = TestModelsFactory.generateSingleUser();
        user = storage.save(user);

        User newUser = new UserEntity(0, "NEW NAME", "NEW EMAIL");
        newUser.setId(user.getId());
        User updatedUser = storage.update(newUser);
        User foundUser = storage.findById(user.getId());

        Assert.assertEquals(newUser, updatedUser);
        Assert.assertEquals(newUser, foundUser);
    }

    @Test
    public void clean_should_remove_all_models() {
        List<User> users = TestModelsFactory.generateUsers(10);
        List<User> savedUsers = new ArrayList<>();
        for (User user : users) {
            savedUsers.add(storage.save(user));
        }
        Assert.assertEquals(savedUsers.size(), storage.findAll().size());
        int index = 0;
        for (User user : storage.findAll()) {
            Assert.assertEquals(savedUsers.get(index++), user);
        }
        storage.clean();
        Assert.assertEquals(0, storage.findAll().size());
    }

}