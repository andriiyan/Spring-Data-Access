package com.github.andriiyan.spring_data_access.impl.dao;

import com.github.andriiyan.spring_data_access.api.dao.UserAccountDao;
import com.github.andriiyan.spring_data_access.api.model.UserAccount;
import com.github.andriiyan.spring_data_access.impl.model.UserAccountEntity;
import com.github.andriiyan.spring_data_access.impl.storage.hibernate.ISessionFactoryProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;

import java.util.Optional;

public class UserAccountDaoImpl extends BaseDao<UserAccount, UserAccountEntity> implements UserAccountDao {

    private static final Logger logger = LoggerFactory.getLogger(EventDaoImpl.class);

    public UserAccountDaoImpl(ISessionFactoryProvider sessionFactoryProvider) {
        super(sessionFactoryProvider);
    }

    @Override
    public UserAccount refillUser(double amount, long userId) {
        UserAccount userAccount = getUserAmount(userId);
        userAccount.setAmount(userAccount.getAmount() + amount);
        userAccount = save(userAccount);
        logger.debug("refillUser was invoked with the amount: {}, userId: {} and returned {}", amount, userId, userAccount);
        return userAccount;
    }

    @Override
    public UserAccount getUserAmount(long userId) {
        Optional<UserAccount> result = findById(userId);
        logger.debug("getUserAmount was invoked with the userId: {} and returned {}", userId, result);
        return result.get();
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }

    @Override
    @NonNull
    protected Class<UserAccountEntity> getEntityClass() {
        return UserAccountEntity.class;
    }
}
