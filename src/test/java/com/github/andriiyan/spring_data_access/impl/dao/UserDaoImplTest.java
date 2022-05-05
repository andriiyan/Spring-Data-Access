package com.github.andriiyan.spring_data_access.impl.dao;

import com.github.andriiyan.spring_data_access.TestConfiguration;
import com.github.andriiyan.spring_data_access.api.model.User;
import com.github.andriiyan.spring_data_access.impl.model.UserEntity;
import jakarta.persistence.PersistenceException;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class UserDaoImplTest extends TestConfiguration {

    private final UserDaoImpl userDao = new UserDaoImpl(configuration);

    @Test
    public void getUserByEmail() {
        final String email = "email@test.com";

        // save some users
        userDao.save(new UserEntity("name", "email"));
        User user = userDao.save(new UserEntity("user", email));
        userDao.save(new UserEntity("user1", email + "1"));

        Assert.assertEquals(user, userDao.getUserByEmail(email));

        // case there are no user with this email
        try {
            userDao.getUserByEmail(email + "-no-existing");
            Assert.fail("Should fail because there is no user with email:" + email + " in the database");
        } catch (NoSuchElementException ignore) {
            // this is a success case
        }

    }

    @Test
    public void getUsersByName() {
        final String searchingName = "name";
        int pageSize = 3;
        int pageNum = 2;

        final List<User> users = new ArrayList<>();

        for (int i = 0; i < (pageNum * pageSize) + pageSize; i++) {
            // save some not suitable users
            userDao.save(new UserEntity("not suitable" + i, "email" + i));
            users.add(userDao.save(new UserEntity(searchingName + i, "email@" + i)));
        }

        Assert.assertEquals(
                users.subList(pageSize * pageNum, pageSize * pageNum + pageSize),
                userDao.getUsersByName(searchingName, pageSize, pageNum)
        );

    }

    // Testing BaseDao functions

    @Test
    public void getEntityClass() {
        Assert.assertEquals(UserEntity.class, userDao.getEntityClass());
    }

    @Test
    public void save_shouldSaveUser() {
        UserEntity savingUser = new UserEntity("Name", "email");
        UserEntity savedUser = userDao.save(savingUser);

        // ID should be sett by saving method, so both entities should have identical fields
        Assert.assertEquals(savingUser, savedUser);

        User getUser = userDao.findById(savedUser.getId()).get();
        Assert.assertEquals(savedUser, getUser);
    }

    @Test
    public void save_shouldFailForSameName() {
        UserEntity savingUser = new UserEntity("Name", "email");
        UserEntity savedUser = userDao.save(savingUser);
        try {
            userDao.save(new UserEntity(savedUser.getName(), "test"));
            Assert.fail("Should fail because database already has a user with name: " + savedUser.getName());
        } catch (PersistenceException ignored) {

        }
    }

    @Test
    public void save_shouldFailForSameEmail() {
        UserEntity savingUser = new UserEntity("Name", "email");
        UserEntity savedUser = userDao.save(savingUser);
        try {
            userDao.save(new UserEntity("Test", savedUser.getEmail()));
            Assert.fail("Should fail because database already has a user with email: " + savedUser.getEmail());
        } catch (PersistenceException ignored) {

        }
    }

    @Test
    public void save_shouldUpdateUserWithId() {
        final String newName = "new name";
        final String newEmail = "new email";
        User user = userDao.save(new UserEntity("name", "email"));
        user.setName(newName);
        user.setEmail(newEmail);
        User updatedUser = userDao.save(user);

        Assert.assertEquals(newEmail, updatedUser.getEmail());
        Assert.assertEquals(newName, updatedUser.getName());
    }

    @Test
    public void saveAll() {
        List<User> users = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            users.add(new UserEntity("name #" + i, "email" + i));
        }

        Iterable<User> savedUsers = userDao.saveAll(users);
        Assert.assertEquals(users, savedUsers);
    }

    @Test
    public void findById() {
        List<User> users = new ArrayList<>();

        int size = 10;
        for (int i = 0; i < size; i++) {
            users.add(new UserEntity("name #" + i, "email" + i));
        }

        Iterable<User> savedUsers = userDao.saveAll(users);

        for (User user : savedUsers) {
            Assert.assertEquals(user, userDao.findById(user.getId()).get());
        }

        // should return an empty optional, because user is not in the database
        Assert.assertTrue(userDao.findById(size + 2L).isEmpty());
    }

    @Test
    public void findBy() {
        List<User> users = new ArrayList<>();

        int size = 10;
        for (int i = 0; i < size; i++) {
            users.add(new UserEntity("name #" + i, "email" + i));
        }

        Iterable<User> savedUsers = userDao.saveAll(users);

        for (User user : savedUsers) {
            Assert.assertEquals(user, userDao.findBy(((root, criteriaBuilder) -> criteriaBuilder.equal(root.get("email"), user.getEmail()))).get());
        }

    }

    @Test
    public void existsById() {
        List<User> users = new ArrayList<>();

        int size = 10;
        for (int i = 0; i < size; i++) {
            users.add(new UserEntity("name #" + i, "email" + i));
        }

        Iterable<User> savedUsers = userDao.saveAll(users);

        for (User user : savedUsers) {
            Assert.assertTrue(userDao.existsById(user.getId()));
        }

        Assert.assertFalse(userDao.existsById(size + 2L));
    }

    @Test
    public void findAll() {
        List<User> users = new ArrayList<>();

        int size = 10;
        for (int i = 0; i < size; i++) {
            users.add(new UserEntity("name #" + i, "email" + i));
        }

        Iterable<User> savedUsers = userDao.saveAll(users);
        Assert.assertEquals(savedUsers, userDao.findAll());
    }

    @Test
    public void findAllById() {
        List<User> users = new ArrayList<>();
        List<Long> integers = new ArrayList<>();

        int size = 10;
        for (int i = 0; i < size; i++) {
            users.add(new UserEntity("name #" + i, "email" + i));
            if (i < size / 2) {
                integers.add((long) i);
            }
        }

        List<User> savedUsers = (List<User>) userDao.saveAll(users);
        Assert.assertEquals(savedUsers.subList(0, size / 2 - 1), userDao.findAllById(integers));
    }

    @Test
    public void count() {
        List<User> users = new ArrayList<>();

        int size = 10;
        for (int i = 0; i < size; i++) {
            users.add(new UserEntity("name #" + i, "email" + i));
        }

        List<User> savedUsers = (List<User>) userDao.saveAll(users);
        Assert.assertEquals(size, userDao.count());
    }

    @Test
    public void deleteById() {
        List<User> users = new ArrayList<>();

        int size = 10;
        for (int i = 0; i < size; i++) {
            users.add(new UserEntity("name #" + i, "email" + i));
        }

        List<User> savedUsers = (List<User>) userDao.saveAll(users);
        userDao.deleteById(users.get(0).getId());
        Assert.assertEquals(savedUsers.subList(1, users.size()), userDao.findAll());
    }

    @Test
    public void delete() {
        List<User> users = new ArrayList<>();

        int size = 10;
        for (int i = 0; i < size; i++) {
            users.add(new UserEntity("name #" + i, "email" + i));
        }

        List<User> savedUsers = (List<User>) userDao.saveAll(users);
        userDao.delete(users.get(0));
        Assert.assertEquals(savedUsers.subList(1, users.size()), userDao.findAll());
    }

    @Test
    public void deleteAllById() {
        List<User> users = new ArrayList<>();
        List<Long> ids = new ArrayList<>();

        int size = 10;
        for (int i = 0; i < size; i++) {
            users.add(new UserEntity("name #" + i, "email" + i));
            if (i < size / 2) {
                ids.add((long) i);
            }
        }

        List<User> savedUsers = (List<User>) userDao.saveAll(users);
        userDao.deleteAllById(ids);
        Assert.assertEquals(savedUsers.subList(size / 2 - 1, size), userDao.findAll());
    }

    @Test
    public void deleteAllByEntity() {
        List<User> users = new ArrayList<>();

        int size = 10;
        for (int i = 0; i < size; i++) {
            users.add(new UserEntity("name #" + i, "email" + i));
        }

        List<User> savedUsers = (List<User>) userDao.saveAll(users);
        userDao.deleteAll(users);
        Assert.assertTrue(((List<User>) userDao.findAll()).isEmpty());
    }

    @Test
    public void deleteAll() {
        List<User> users = new ArrayList<>();

        int size = 10;
        for (int i = 0; i < size; i++) {
            users.add(new UserEntity("name #" + i, "email" + i));
        }

        List<User> savedUsers = (List<User>) userDao.saveAll(users);
        userDao.deleteAll();
        Assert.assertTrue(((List<User>) userDao.findAll()).isEmpty());
        Assert.assertEquals(0, userDao.count());
    }

}