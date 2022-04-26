package com.github.andriiyan.spring_data_access.impl.service;

import com.github.andriiyan.spring_data_access.api.dao.UserAccountDao;
import com.github.andriiyan.spring_data_access.api.model.UserAccount;
import com.github.andriiyan.spring_data_access.api.service.UserAccountService;
import com.github.andriiyan.spring_data_access.impl.dao.exception.ModelNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class UserAccountServiceImpl implements UserAccountService {

    private static final Logger logger = LoggerFactory.getLogger(UserAccountServiceImpl.class);

    @Autowired
    private UserAccountDao userAccountDao;

    @Override
    public UserAccount refillUser(double amount, long userId) throws ModelNotFoundException {
        UserAccount result = userAccountDao.refillUser(amount, userId);
        logger.debug("refillUser was invoked with the amount: {}, userId: {}, result is {}", amount, userId, result);
        return result;
    }

    @Override
    public UserAccount getUserAmount(long userId) {
        UserAccount result = userAccountDao.getUserAmount(userId);
        logger.debug("getUserAmount was invoked with the userId: {}, result is {}", userId, result);
        return result;
    }

    public void setUserAccountDao(UserAccountDao userAccountDao) {
        this.userAccountDao = userAccountDao;
    }
}
