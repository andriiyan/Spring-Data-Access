package com.github.andriiyan.spring_data_access.api.dao;

import com.github.andriiyan.spring_data_access.api.model.UserAccount;
import com.github.andriiyan.spring_data_access.impl.dao.exception.ModelNotFoundException;

public interface UserAccountDao {
    /**
     * Refills user's account.
     * @param amount amount that wil be added to the user's account.
     * @param userId id of the user.
     * @return new user's account amount.
     */
    UserAccount refillUser(double amount, long userId) throws ModelNotFoundException;

    /**
     * @param userId id of the user.
     * @return user's amount.
     */
    UserAccount getUserAmount(long userId);
}
