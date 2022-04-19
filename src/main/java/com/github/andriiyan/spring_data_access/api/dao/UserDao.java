package com.github.andriiyan.spring_data_access.api.dao;

import com.github.andriiyan.spring_data_access.api.model.User;

import java.util.List;

public interface UserDao extends BaseDao<User> {

    User getUserByEmail(String email);

    List<User> getUsersByName(String name, int pageSize, int pageNum);

}
