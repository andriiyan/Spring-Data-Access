package com.github.andriiyan.spring_data_access.api.exceptions;

import com.github.andriiyan.spring_data_access.api.model.Event;
import com.github.andriiyan.spring_data_access.api.model.UserAccount;
import org.springframework.lang.NonNull;

public class NotEnoughMoneyException extends Exception {

    public NotEnoughMoneyException(@NonNull Event event, @NonNull UserAccount userAccount) {
        super("Not enough money to perform an operation! User has " + userAccount.getAmount() + ", but required " + event.getTicketPrice());
    }
}
