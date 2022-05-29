package com.github.andriiyan.spring_data_access.impl.service;

import com.github.andriiyan.spring_data_access.api.dao.UserDao;
import com.github.andriiyan.spring_data_access.api.model.User;
import com.github.andriiyan.spring_data_access.api.service.UserService;
import com.github.andriiyan.spring_data_access.impl.model.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private UserDao userDao;

    @Override
    public User getUserById(long userId) {
        final Optional<UserEntity> user = userDao.findById(userId);
        logger.debug("getUserById was invoked with userId={} and returning {}", userId, user);
        return user.get(); // todo get
    }

    @Override
    public User getUserByEmail(String email) {
        final Optional<UserEntity> mUser = userDao.findByEmail(email);
        logger.debug("getUserByEmail was invoked with email={} and returning {}", email, mUser);
        return mUser.get(); // todo get
    }

    @Override
    public List<UserEntity> getUsersByName(String name, int pageSize, int pageNum) {
        final List<UserEntity> users = userDao.findAllByName(name, PageRequest.of(pageNum, pageSize));
        logger.debug("getUsersByName was invoked with name={}, pageSize={}, pageNum={} and returning {}", name, pageSize, pageNum, users);
        return users;
    }

    @Override
    public User createUser(UserEntity user) {
        final UserEntity mUser = userDao.save(user);
        logger.debug("createUser was invoked with user={} and returning {}", user, mUser);
        return mUser;
    }

    @Override
    public User updateUser(UserEntity user) {
        final User mUser = userDao.save(user);
        logger.debug("updateUser was invoked with user={} and returning {}", user, mUser);
        return mUser;
    }

    @Override
    public boolean deleteUser(long userId) {
        userDao.deleteById(userId);
        logger.debug("deleteUser was invoked with userId={}", userId);
        return true;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
