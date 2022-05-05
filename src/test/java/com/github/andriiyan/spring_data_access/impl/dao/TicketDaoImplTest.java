package com.github.andriiyan.spring_data_access.impl.dao;

import com.github.andriiyan.spring_data_access.api.model.Event;
import com.github.andriiyan.spring_data_access.api.model.Ticket;
import com.github.andriiyan.spring_data_access.api.model.User;
import com.github.andriiyan.spring_data_access.TestConfiguration;
import com.github.andriiyan.spring_data_access.impl.model.EventEntity;
import com.github.andriiyan.spring_data_access.impl.model.TicketEntity;
import com.github.andriiyan.spring_data_access.impl.model.UserEntity;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TicketDaoImplTest extends TestConfiguration {

    private final TicketDaoImpl ticketDao = new TicketDaoImpl(configuration);
    private final UserDaoImpl userDao = new UserDaoImpl(configuration);
    private final EventDaoImpl eventDao = new EventDaoImpl(configuration);

    @Test
    public void getBookedTicketsUser() {
        final int pageSize = 5;
        final int pageNum = 2;

        final User user1 = userDao.save(new UserEntity("User 1", "user1@gmail.com"));
        final User user2 = userDao.save(new UserEntity("User 2", "user2@gmail.com"));
        final Event event = eventDao.save(new EventEntity("Title", new Date(), 100));

        final List<Ticket> rightTickets = new ArrayList<>();
        int size = (2 * pageNum * pageSize) + pageSize;
        for (int i = 0; i < size; i++) {
            // save some random tickets
            ticketDao.save(new TicketEntity(event.getId(), user1.getId(), Ticket.Category.PREMIUM, i));
            // tickets that fit query
            rightTickets.add(ticketDao.save(new TicketEntity(event.getId(), user2.getId(), Ticket.Category.BAR, i + size)));
        }

        final List<Ticket> returnedTickets = ticketDao.getBookedTickets(user2, pageSize, pageNum);
        Assert.assertEquals(rightTickets.subList(pageNum * pageSize, (pageNum + 1) * pageSize), returnedTickets);
    }

    @Test
    public void getBookedTicketsEvent() {
        final int pageSize = 5;
        final int pageNum = 2;

        final Event event1 = eventDao.save(new EventEntity("Event #1", new Date(), 100));
        final Event event2 = eventDao.save(new EventEntity("Event #2", new Date(), 70));
        final User user = userDao.save(new UserEntity("name", "email"));

        final List<Ticket> rightTickets = new ArrayList<>();

        int size = (2 * pageNum * pageSize) + pageSize;
        for (int i = 0; i < size; i++) {
            // save some random tickets
            ticketDao.save(new TicketEntity(event1.getId(), user.getId(), Ticket.Category.PREMIUM, i));
            // tickets that fit query
            rightTickets.add(ticketDao.save(new TicketEntity(event2.getId(), user.getId(), Ticket.Category.BAR, size + i)));
        }

        final List<Ticket> returnedTickets = ticketDao.getBookedTickets(event2, pageSize, pageNum);
        Assert.assertEquals(rightTickets.subList(pageNum * pageSize, (pageNum + 1) * pageSize), returnedTickets);
    }

}