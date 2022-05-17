package com.github.andriiyan.spring_data_access.api.dao;

import com.github.andriiyan.spring_data_access.impl.model.UserAccountEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserAccountDao extends CrudRepository<UserAccountEntity, Long> {

    Optional<UserAccountEntity> findByUserId(long userId);
}
