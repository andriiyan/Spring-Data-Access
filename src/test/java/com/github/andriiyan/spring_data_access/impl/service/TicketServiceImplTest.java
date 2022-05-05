package com.github.andriiyan.spring_data_access.impl.service;

import com.github.andriiyan.spring_data_access.api.dao.TicketDao;
import com.github.andriiyan.spring_data_access.api.model.Event;
import com.github.andriiyan.spring_data_access.api.model.Ticket;
import com.github.andriiyan.spring_data_access.api.model.User;
import com.github.andriiyan.spring_data_access.impl.model.EventEntity;
import com.github.andriiyan.spring_data_access.impl.model.TicketEntity;
import com.github.andriiyan.spring_data_access.impl.model.UserEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class TicketServiceImplTest {

    @Mock
    private TicketDao ticketDao;

    @InjectMocks
    private TicketServiceImpl ticketService;

    @Test
    public void bookTicket_shouldInvokeDao() {
        final Ticket returningTicket = new TicketEntity(1, 1, Ticket.Category.BAR, 2);
        final long userId = 1;
        final long eventId = 1;
        final int place = 12;
        final Ticket.Category category = Ticket.Category.PREMIUM;
        Mockito.when(ticketDao.save(Mockito.any())).thenReturn(returningTicket);

        final Ticket returnedTicket = ticketService.bookTicket(userId, eventId, place, category);

        Assert.assertEquals(returningTicket, returnedTicket);
        Mockito.verify(ticketDao).save(Mockito.any());
    }

    @Test
    public void getBookedTicketsUser_shouldReturnCorrectTickets() {
        final int pageSize = 5;
        final int pageNum = 2;

        final User user = new UserEntity("name", "email");
        final List<Ticket> allTickets = new ArrayList<>();
        for (int i = 0; i < pageSize * (pageNum + 1); i++) {
            allTickets.add(new TicketEntity(i, i, Ticket.Category.BAR, i));
        }        Mockito.when(ticketDao.getBookedTickets(user, pageSize, pageNum)).thenReturn(allTickets);

        final List<Ticket> returnedTickets = ticketService.getBookedTickets(user, pageSize, pageNum);

        Assert.assertEquals(allTickets, returnedTickets);
        Mockito.verify(ticketDao).getBookedTickets(user, pageSize, pageNum);
    }

    @Test
    public void getBookedTicketsEvent_shouldReturnCorrectTickets() {
        final int pageSize = 5;
        final int pageNum = 2;

        final Event event = new EventEntity("test", new Date(), 20);
        final List<Ticket> allTickets = new ArrayList<>();
        for (int i = 0; i < pageSize * (pageNum + 1); i++) {
            allTickets.add(new TicketEntity(i, i, Ticket.Category.BAR, i));
        }
        Mockito.when(ticketDao.getBookedTickets(event, pageSize, pageNum)).thenReturn(allTickets);

        final List<Ticket> returnedTickets = ticketService.getBookedTickets(event, pageSize, pageNum);

        Assert.assertEquals(allTickets, returnedTickets);
        Mockito.verify(ticketDao).getBookedTickets(event, pageSize, pageNum);
    }


    @Test
    public void cancelTicket_shouldReturnSameModelAsDao() {
        final long ticketId = 100;
        Mockito.doNothing().when(ticketDao).deleteById(ticketId);

        Assert.assertTrue(ticketService.cancelTicket(ticketId));
        Mockito.verify(ticketDao).deleteById(ticketId);
    }

}