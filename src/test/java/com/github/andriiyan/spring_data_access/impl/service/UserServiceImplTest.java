package com.github.andriiyan.spring_data_access.impl.service;

import com.github.andriiyan.spring_data_access.api.dao.UserDao;
import com.github.andriiyan.spring_data_access.api.model.User;
import com.github.andriiyan.spring_data_access.impl.TestModelsFactory;
import com.github.andriiyan.spring_data_access.impl.dao.exception.ModelNotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @Mock
    private UserDao userDao;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void getUserById() {
        final long userId = 123;
        final User returningUser = TestModelsFactory.generateSingleUser();
        Mockito.when(userDao.findById(userId)).thenReturn(returningUser);

        final User returnedUser = userService.getUserById(userId);

        Assert.assertEquals(returningUser, returnedUser);
        Mockito.verify(userDao).findById(userId);
    }

    @Test
    public void getUserByEmail() {
        final String email = "email@test.com";
        User returningUser = TestModelsFactory.generateSingleUser();
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
        List<User> returningUsers = TestModelsFactory.generateUsers((pageNum + 1) * pageSize);
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
        final User user = TestModelsFactory.generateSingleUser();
        final User returningUser = TestModelsFactory.generateSingleUser();
        Mockito.when(userDao.save(user)).thenReturn(returningUser);

        final User returnedUser = userService.createUser(user);

        Assert.assertEquals(returningUser, returnedUser);
        Mockito.verify(userDao).save(user);
    }

    @Test
    public void updateUser() throws ModelNotFoundException {
        final User user = TestModelsFactory.generateSingleUser();
        final User returningUser = TestModelsFactory.generateSingleUser();
        Mockito.when(userDao.update(user)).thenReturn(returningUser);

        final User returnedUser = userService.updateUser(user);

        Assert.assertEquals(returningUser, returnedUser);
        Mockito.verify(userDao).update(user);
    }

    @Test
    public void deleteUser() {
        final User user = TestModelsFactory.generateSingleUser();
        Mockito.when(userDao.delete(user.getId())).thenReturn(true);

        final boolean result = userService.deleteUser(user.getId());

        Assert.assertTrue(result);
        Mockito.verify(userDao).delete(user.getId());
    }
}