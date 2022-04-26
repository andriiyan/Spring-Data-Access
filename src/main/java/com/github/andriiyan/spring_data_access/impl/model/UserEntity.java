package com.github.andriiyan.spring_data_access.impl.model;

import com.github.andriiyan.spring_data_access.api.model.User;
import com.github.andriiyan.spring_data_access.impl.utils.JsonInstanceCreator;
import com.google.gson.Gson;
import jakarta.persistence.*;

import java.util.Arrays;
import java.util.Collection;

@Entity
@Table(name = "users")
public class UserEntity implements User {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    public UserEntity() {
    }

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
