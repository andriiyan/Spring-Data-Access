package com.github.andriiyan.spring_data_access.impl.service;

import com.github.andriiyan.spring_data_access.api.dao.TicketDao;
import com.github.andriiyan.spring_data_access.api.model.Event;
import com.github.andriiyan.spring_data_access.api.model.Ticket;
import com.github.andriiyan.spring_data_access.api.model.User;
import com.github.andriiyan.spring_data_access.api.service.TicketService;
import com.github.andriiyan.spring_data_access.impl.model.TicketEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

class TicketServiceImpl implements TicketService {

    private static final Logger logger = LoggerFactory.getLogger(TicketServiceImpl.class);

    @Autowired
    private TicketDao ticketDao;

    @Override
    public Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category) {
        final Ticket ticket = ticketDao.save(new TicketEntity(0, eventId, userId, category, place));
        logger.debug("bookTicket was invoked with userId={}, eventId={}, place={}, category={} and returning {}", userId, eventId, place, category, ticket);
        return ticket;
    }

    @Override
    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
        final List<Ticket> tickets = ticketDao.getBookedTickets(user, pageSize, pageNum);
        logger.debug("getBookedTickets was invoked user={}, pageSize={}, pageNum={} and returning {}", user, pageSize, pageNum, tickets);
        return tickets;
    }

    @Override
    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        final List<Ticket> tickets = ticketDao.getBookedTickets(event, pageSize, pageNum);
        logger.debug("getBookedTickets was invoked with event={}, pageSize={}, pageNum={} and returning {}", event, pageSize, pageNum, tickets);
        return tickets;
    }

    @Override
    public boolean cancelTicket(long ticketId) {
        ticketDao.deleteById(ticketId);
        logger.debug("cancelTicket was invoked with ticketId={}", ticketId);
        return true;
    }

    public void setTicketDao(TicketDao ticketDao) {
        this.ticketDao = ticketDao;
    }
}
