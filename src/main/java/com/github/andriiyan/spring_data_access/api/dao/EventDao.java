package com.github.andriiyan.spring_data_access.api.dao;

import com.github.andriiyan.spring_data_access.impl.model.EventEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;
import java.util.List;

public interface EventDao extends PagingAndSortingRepository<EventEntity, Long> {

    List<EventEntity> findAllByTitle(String title, Pageable pageable);

    List<EventEntity> findAllByDate(Date day, Pageable pageable);

}
