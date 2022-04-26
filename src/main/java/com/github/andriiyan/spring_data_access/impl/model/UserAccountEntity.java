package com.github.andriiyan.spring_data_access.impl.model;

import com.github.andriiyan.spring_data_access.api.model.UserAccount;
import jakarta.persistence.*;

@Entity
@Table(name = "user_account")
public class UserAccountEntity implements UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private long id;

    @Column(name = "user_id", nullable = false)
    private long userId;

    @Column(name = "amount", nullable = false)
    private double amount;

    public UserAccountEntity() {}

    public UserAccountEntity(long id, long userId, double amount) {
        this.amount = amount;
        this.id = id;
        this.userId = userId;
    }

    public UserAccountEntity(long userId, double amount) {
        this.id = 0;
        this.userId = userId;
        this.amount = 0;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public double getAmount() {
        return amount;
    }

    @Override
    public void setAmount(double amount) {
        this.amount = amount;
    }
}
