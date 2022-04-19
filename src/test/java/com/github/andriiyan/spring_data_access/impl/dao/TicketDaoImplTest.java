package com.github.andriiyan.spring_data_access.impl.dao;

import com.github.andriiyan.spring_data_access.api.model.Event;
import com.github.andriiyan.spring_data_access.api.model.Ticket;
import com.github.andriiyan.spring_data_access.api.model.User;
import com.github.andriiyan.spring_data_access.api.storage.Storage;
import com.github.andriiyan.spring_data_access.impl.TestModelsFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class TicketDaoImplTest {

    @Mock
    private Storage<Ticket> ticketStorage;
    @InjectMocks
    private TicketDaoImpl ticketDao;

    @Test
    public void getBookedTicketsUser_shouldReturnCorrectTickets() {
        final int pageSize = 5;
        final int pageNum = 2;

        final User user = TestModelsFactory.generateSingleUser();
        final List<Ticket> allTickets = TestModelsFactory.generateTickets(pageSize * (pageNum + 1), new TestModelsFactory.DefaultTicketCountInstanceFactory() {
            @Override
            protected long userId(int count) {
                return user.getId();
            }
        });
        Mockito.when(ticketStorage.findAll()).thenReturn(allTickets);


        final List<Ticket> returnedTickets = ticketDao.getBookedTickets(user, pageSize, pageNum);

        Assert.assertEquals(allTickets.subList(pageNum * pageSize, (pageNum + 1) * pageSize), returnedTickets);
        Mockito.verify(ticketStorage).findAll();
    }

    @Test
    public void getBookedTicketsEvent_shouldReturnCorrectTickets() {
        final int pageSize = 5;
        final int pageNum = 2;

        final Event event = TestModelsFactory.generateSingleEvent();
        final List<Ticket> allTickets = TestModelsFactory.generateTickets(pageSize * (pageNum + 1), new TestModelsFactory.DefaultTicketCountInstanceFactory() {
            @Override
            protected long eventId(int count) {
                return event.getId();
            }
        });
        Mockito.when(ticketStorage.findAll()).thenReturn(allTickets);


        final List<Ticket> returnedTickets = ticketDao.getBookedTickets(event, pageSize, pageNum);

        Assert.assertEquals(allTickets.subList(pageNum * pageSize, (pageNum + 1) * pageSize), returnedTickets);
        Mockito.verify(ticketStorage).findAll();
    }


}