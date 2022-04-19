package com.github.andriiyan.spring_data_access.impl.model;

import com.github.andriiyan.spring_data_access.api.model.User;
import com.github.andriiyan.spring_data_access.impl.utils.JsonInstanceCreator;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.Collection;

public class UserEntity implements User {
    private static final long serialVersionUID = 1L;

    private long id;
    private String name;
    private String email;

    public UserEntity(long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserImpl{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public static class UserJsonInstanceCreator implements JsonInstanceCreator<User> {

        @Override
        public Collection<User> createInstances(String source, Gson gson) {
            return Arrays.asList(gson.fromJson(source, UserEntity[].class));
        }

        @Override
        public Class<User> getType() {
            return User.class;
        }
    }
}
