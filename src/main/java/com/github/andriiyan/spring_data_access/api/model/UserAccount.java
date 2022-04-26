package com.github.andriiyan.spring_data_access.api.model;

public interface UserAccount extends Identifierable {
    double getAmount();

    void setAmount(double amount);

    long getUserId();
}
