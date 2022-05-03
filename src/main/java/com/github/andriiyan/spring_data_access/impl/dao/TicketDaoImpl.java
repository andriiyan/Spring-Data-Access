package com.github.andriiyan.spring_data_access.impl.dao;

import com.github.andriiyan.spring_data_access.api.dao.TicketDao;
import com.github.andriiyan.spring_data_access.api.model.Event;
import com.github.andriiyan.spring_data_access.api.model.Ticket;
import com.github.andriiyan.spring_data_access.api.model.User;
import com.github.andriiyan.spring_data_access.impl.model.TicketEntity;
import com.github.andriiyan.spring_data_access.impl.storage.hibernate.ISessionFactoryProvider;
import com.github.andriiyan.spring_data_access.impl.utils.ListUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;

import java.util.List;

class TicketDaoImpl extends BaseDao<Ticket, TicketEntity> implements TicketDao {

    private static final Logger logger = LoggerFactory.getLogger(TicketDaoImpl.class);

    public TicketDaoImpl(ISessionFactoryProvider sessionFactoryProvider) {
        super(sessionFactoryProvider);
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    @NonNull
    protected Class<TicketEntity> getEntityClass() {
        return TicketEntity.class;
    }

    @Override
    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
        final Iterable<Ticket> tickets = findPaging(pageNum, pageSize, ((root, criteriaBuilder) -> criteriaBuilder.equal(root.get("user_id").as(Long.class), user.getId())));
        logger.debug("getBookedTickets was invoked user={}, pageSize={}, pageNum={} and returning {}", user, pageSize, pageNum, tickets);
        return ListUtils.fromIterable(tickets);
    }

    @Override
    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        final Iterable<Ticket> tickets = findPaging(pageNum, pageSize, ((root, criteriaBuilder) -> criteriaBuilder.equal(root.get("event_id").as(Long.class), event.getId())));
        logger.debug("getBookedTickets was invoked with event={}, pageSize={}, pageNum={} and returning {}", event, pageSize, pageNum, tickets);
        return ListUtils.fromIterable(tickets);
    }
}
