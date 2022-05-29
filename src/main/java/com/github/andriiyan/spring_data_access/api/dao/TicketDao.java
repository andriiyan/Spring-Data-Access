package com.github.andriiyan.spring_data_access.api.dao;

import com.github.andriiyan.spring_data_access.impl.model.TicketEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface TicketDao extends PagingAndSortingRepository<TicketEntity, Long> {

    List<TicketEntity> findAllByUserId(long userId, Pageable pageable);

    List<TicketEntity> findAllByEventId(long eventId, Pageable pageable);

}
