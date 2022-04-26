package com.github.andriiyan.spring_data_access.impl.dao;

import com.github.andriiyan.spring_data_access.api.dao.UserAccountDao;
import com.github.andriiyan.spring_data_access.api.model.UserAccount;
import com.github.andriiyan.spring_data_access.api.storage.Storage;
import com.github.andriiyan.spring_data_access.impl.dao.exception.ModelNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserAccountDaoImpl extends BaseDaoImpl<UserAccount> implements UserAccountDao {

    private static final Logger logger = LoggerFactory.getLogger(EventDaoImpl.class);

    public UserAccountDaoImpl(Storage<UserAccount> storage) {
        super(storage);
    }

    @Override
    public UserAccount refillUser(double amount, long userId) throws ModelNotFoundException {
        UserAccount userAccount = getUserAmount(userId);
        userAccount.setAmount(userAccount.getAmount() + amount);
        userAccount = update(userAccount);
        logger.debug("refillUser was invoked with the amount: {}, userId: {} and returned {}", amount, userId, userAccount);
        return userAccount;
    }

    @Override
    public UserAccount getUserAmount(long userId) {
        UserAccount result = findAll().stream().filter(ua -> ua.getUserId() == userId).findFirst().get();
        logger.debug("getUserAmount was invoked with the userId: {} and returned {}", userId, result);
        return result;
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }
}
