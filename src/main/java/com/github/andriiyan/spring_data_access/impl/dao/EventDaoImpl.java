package com.github.andriiyan.spring_data_access.impl.dao;

import com.github.andriiyan.spring_data_access.api.dao.EventDao;
import com.github.andriiyan.spring_data_access.api.model.Event;
import com.github.andriiyan.spring_data_access.impl.model.EventEntity;
import com.github.andriiyan.spring_data_access.impl.storage.hibernate.ISessionFactoryProvider;
import com.github.andriiyan.spring_data_access.impl.utils.ListUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;

import java.util.Date;
import java.util.List;

class EventDaoImpl extends BaseDao<Event, EventEntity> implements EventDao {

    private static final Logger logger = LoggerFactory.getLogger(EventDaoImpl.class);

    public EventDaoImpl(ISessionFactoryProvider sessionFactoryProvider) {
        super(sessionFactoryProvider);
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    @NonNull
    protected Class<EventEntity> getEntityClass() {
        return EventEntity.class;
    }

    @Override
    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        final Iterable<Event> events = findPaging(pageNum, pageSize, (root, criteriaBuilder) -> criteriaBuilder.like(root.get("title").as(String.class), "%" + title + "%"));
        logger.debug("getEventsByTitle was invoked with title={}, pageSize={}, pageNum={} and returning {}", title, pageSize, pageNum, events);
        return ListUtils.fromIterable(events);
    }

    @Override
    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
        final java.sql.Date requestedDay = new java.sql.Date(day.getTime());
        final Iterable<Event> events = findPaging(
                pageNum,
                pageSize,
                (root, criteriaBuilder) -> criteriaBuilder.equal(root.get("date").as(java.sql.Date.class), requestedDay)
        );
        logger.debug("getEventsForDay was invoked with day={}, pageSize={}, pageNum={} and returning {}", day, pageSize, pageNum, events);
        return ListUtils.fromIterable(events);
    }
}
