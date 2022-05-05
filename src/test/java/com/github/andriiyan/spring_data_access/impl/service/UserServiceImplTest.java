package com.github.andriiyan.spring_data_access.impl.service;

import com.github.andriiyan.spring_data_access.api.dao.UserDao;
import com.github.andriiyan.spring_data_access.api.model.User;
import com.github.andriiyan.spring_data_access.impl.model.UserEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @Mock
    private UserDao userDao;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void getUserById() {
        final long userId = 123;
        final Optional<User> returningUser = Optional.of(new UserEntity("name", "email"));
        Mockito.when(userDao.findById(userId)).thenReturn(returningUser);

        final User returnedUser = userService.getUserById(userId);

        Assert.assertEquals(returningUser.get(), returnedUser);
        Mockito.verify(userDao).findById(userId);
    }

    @Test
    public void getUserByEmail() {
        final String email = "email@test.com";
        User returningUser = new UserEntity("name", "email");
        returningUser.setEmail(email);
        Mockito.when(userDao.getUserByEmail(email)).thenReturn(returningUser);

        final User returnedUser1 = userService.getUserByEmail(email);

        Assert.assertEquals(returningUser, returnedUser1);
        Mockito.verify(userDao).getUserByEmail(email);
    }

    @Test
    public void getUsersByName() {
        final String searchingName = "name";
        int pageSize = 3;
        int pageNum = 2;

        List<User> returningUsers = new ArrayList<>();
        for (int i = 0; i < (pageNum + 1) * pageSize; i++) {
            returningUsers.add(new UserEntity("name " + i, "email " + i));
        }

        Mockito.when(userDao.getUsersByName(searchingName, pageSize, pageNum)).thenReturn(returningUsers);

        List<User> returnedUsers = userService.getUsersByName(searchingName, pageSize, pageNum);

        Assert.assertEquals(returningUsers, returnedUsers);
        Mockito.verify(userDao).getUsersByName(searchingName, pageSize, pageNum);

        // case when there are no users that contains that name
        returningUsers = Collections.emptyList();
        Mockito.when(userDao.getUsersByName(searchingName, pageSize, pageNum)).thenReturn(returningUsers);

        returnedUsers = userService.getUsersByName(searchingName, pageSize, pageNum);
        Assert.assertEquals(returningUsers, returnedUsers);
        Mockito.verify(userDao, Mockito.times(2)).getUsersByName(searchingName, pageSize, pageNum);
    }

    @Test
    public void createUser() {
        final User user = new UserEntity("name", "email");
        final User returningUser = new UserEntity("name1", "email1");
        Mockito.when(userDao.save(user)).thenReturn(returningUser);

        final User returnedUser = userService.createUser(user);

        Assert.assertEquals(returningUser, returnedUser);
        Mockito.verify(userDao).save(user);
    }

    @Test
    public void updateUser() {
        final User user = new UserEntity("name", "email");
        final User returningUser = new UserEntity("name1", "email1");
        Mockito.when(userDao.save(user)).thenReturn(returningUser);

        final User returnedUser = userService.updateUser(user);

        Assert.assertEquals(returningUser, returnedUser);
        Mockito.verify(userDao).save(user);
    }

    @Test
    public void deleteUser() {
        long userId = 123;
        Mockito.doNothing().when(userDao).deleteById(userId);

        final boolean result = userService.deleteUser(userId);

        Assert.assertTrue(result);
        Mockito.verify(userDao).deleteById(userId);
    }
}