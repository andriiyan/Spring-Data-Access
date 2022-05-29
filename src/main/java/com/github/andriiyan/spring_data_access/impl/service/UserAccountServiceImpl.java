package com.github.andriiyan.spring_data_access.impl.service;

import com.github.andriiyan.spring_data_access.api.dao.UserAccountDao;
import com.github.andriiyan.spring_data_access.api.model.UserAccount;
import com.github.andriiyan.spring_data_access.api.service.UserAccountService;
import com.github.andriiyan.spring_data_access.impl.model.UserAccountEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class UserAccountServiceImpl implements UserAccountService {

    private static final Logger logger = LoggerFactory.getLogger(UserAccountServiceImpl.class);

    private UserAccountDao userAccountDao;

    @Override
    public UserAccount refillUser(double amount, long userId) {
        Optional<UserAccountEntity> previousAmount = userAccountDao.findByUserId(userId);
        UserAccountEntity newAccountAmount;
        if (previousAmount.isPresent()) {
            newAccountAmount = previousAmount.get();
            newAccountAmount.add(amount);
        } else {
            newAccountAmount = new UserAccountEntity(userId, amount);
        }
        UserAccount result = userAccountDao.save(newAccountAmount);
        logger.debug("refillUser was invoked with the amount: {}, userId: {}, result is {}", amount, userId, result);
        return result;
    }

    @Override
    public UserAccount getUserAmount(long userId) {
        Optional<UserAccountEntity> result = userAccountDao.findByUserId(userId);
        logger.debug("getUserAmount was invoked with the userId: {}, result is {}", userId, result);
        return result.get();
    }

    public void setUserAccountDao(UserAccountDao userAccountDao) {
        this.userAccountDao = userAccountDao;
    }
}
