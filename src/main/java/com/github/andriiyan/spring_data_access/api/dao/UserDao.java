package com.github.andriiyan.spring_data_access.api.dao;

import com.github.andriiyan.spring_data_access.impl.model.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface UserDao extends PagingAndSortingRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    List<UserEntity> findAllByName(String name, Pageable pageable);

}
