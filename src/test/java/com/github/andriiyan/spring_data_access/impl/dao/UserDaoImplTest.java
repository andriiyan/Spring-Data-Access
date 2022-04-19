package com.github.andriiyan.spring_data_access.impl.dao;

import com.github.andriiyan.spring_data_access.api.model.User;
import com.github.andriiyan.spring_data_access.api.storage.Storage;
import com.github.andriiyan.spring_data_access.impl.TestModelsFactory;
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
public class UserDaoImplTest {

    @Mock
    private Storage<User> userStorage;
    @InjectMocks
    private UserDaoImpl userDao;

    @Test
    public void getUserByEmail() {
        final String email = "email@test.com";
        User returningUser = TestModelsFactory.generateSingleUser();
        returningUser.setEmail(email);
        Mockito.when(userDao.findAll()).thenReturn(Collections.singletonList(returningUser));

        final User returnedUser1 = userDao.getUserByEmail(email);

        Assert.assertEquals(returningUser, returnedUser1);
        Mockito.verify(userStorage).findAll();

        // case when there is no user with such email
        returningUser.setEmail("");

        final User returnedUser2 = userDao.getUserByEmail(email);

        Assert.assertNull(returnedUser2);
        Mockito.verify(userStorage, Mockito.times(2)).findAll();

        // case when it's more than a 1 user with such email
        final List<User> userList = TestModelsFactory.generateUsers(3);
        userList.forEach(user -> user.setEmail(email));
        Mockito.when(userStorage.findAll()).thenReturn(userList);

        final User returnedUser3 = userDao.getUserByEmail(email);

        Assert.assertEquals(userList.get(0), returnedUser3);
        Mockito.verify(userStorage, Mockito.times(3)).findAll();
    }

    @Test
    public void getUsersByName() {
        final String searchingName = "name";
        int pageSize = 3;
        int pageNum = 2;
        List<User> returningUsers = TestModelsFactory.generateUsers((pageNum + 1) * pageSize,
                new TestModelsFactory.DefaultUserCountInstanceFactory() {
                    @Override
                    protected String name(int count) {
                        return searchingName + count;
                    }
                });
        Mockito.when(userStorage.findAll()).thenReturn(returningUsers);

        List<User> returnedUsers = userDao.getUsersByName(searchingName, pageSize, pageNum);

        Assert.assertEquals(returningUsers.subList(pageNum * pageSize, (pageNum + 1) * pageSize), returnedUsers);
        Mockito.verify(userStorage).findAll();

        // case when there are no users that contains that name
        returningUsers = Collections.emptyList();
        Mockito.when(userStorage.findAll()).thenReturn(returningUsers);

        returnedUsers = userDao.getUsersByName(searchingName, pageSize, pageNum);
        Assert.assertEquals(returningUsers, returnedUsers);
        Mockito.verify(userStorage, Mockito.times(2)).findAll();
    }

}