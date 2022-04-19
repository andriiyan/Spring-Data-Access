package com.github.andriiyan.spring_data_access.impl.service;

import com.github.andriiyan.spring_data_access.api.dao.UserDao;
import com.github.andriiyan.spring_data_access.api.model.User;
import com.github.andriiyan.spring_data_access.api.service.UserService;
import com.github.andriiyan.spring_data_access.impl.dao.exception.ModelNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Override
    public User getUserById(long userId) {
        final User user = userDao.findById(userId);
        logger.debug("getUserById was invoked with userId={} and returning {}", userId, user);
        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        final User mUser = userDao.getUserByEmail(email);
        logger.debug("getUserByEmail was invoked with email={} and returning {}", email, mUser);
        return mUser;
    }

    @Override
    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        final List<User> users = userDao.getUsersByName(name, pageSize, pageNum);
        logger.debug("getUsersByName was invoked with name={}, pageSize={}, pageNum={} and returning {}", name, pageSize, pageNum, users);
        return users;
    }

    @Override
    public User createUser(User user) {
        final User mUser = userDao.save(user);
        logger.debug("createUser was invoked with user={} and returning {}", user, mUser);
        return mUser;
    }

    @Override
    public User updateUser(User user) throws ModelNotFoundException {
        final User mUser = userDao.update(user);
        logger.debug("updateUser was invoked with user={} and returning {}", user, mUser);
        return mUser;
    }

    @Override
    public boolean deleteUser(long userId) {
        final boolean result = userDao.delete(userId);
        logger.debug("deleteUser was invoked with userId={} and returning {}", userId, result);
        return result;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
