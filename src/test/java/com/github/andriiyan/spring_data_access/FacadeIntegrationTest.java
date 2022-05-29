package com.github.andriiyan.spring_data_access;

import com.github.andriiyan.spring_data_access.api.exceptions.NotEnoughMoneyException;
import com.github.andriiyan.spring_data_access.api.model.Event;
import com.github.andriiyan.spring_data_access.api.model.Ticket;
import com.github.andriiyan.spring_data_access.api.model.User;
import com.github.andriiyan.spring_data_access.impl.model.EventEntity;
import com.github.andriiyan.spring_data_access.impl.model.UserEntity;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

public class FacadeIntegrationTest extends BaseContainerTest {

    public FacadeIntegrationTest() {
        super("local");
    }

    @Test
    public void refill_book_ticket_check_amount() throws NotEnoughMoneyException {
        final User user = bookingFacade.createUser(new UserEntity("Andrii", "Test"));
        final Event event = bookingFacade.createEvent(new EventEntity("Event", new Date(), 100));
        bookingFacade.refillUser(event.getTicketPrice() + 10, user.getId());
        final Ticket ticket = bookingFacade.bookTicket(user.getId(), event.getId(), 4, Ticket.Category.PREMIUM);
        Assert.assertEquals(user, userDao.findById(user.getId()).get());
        Assert.assertEquals(ticket, ticketDao.findById(ticket.getId()).get());
        Assert.assertEquals(ticket, bookingFacade.getBookedTickets(user, 1, 0).get(0));
    }

    @Test(expected = NotEnoughMoneyException.class)
    public void should_throw_an_exception_if_user_has_no_enough_money() throws NotEnoughMoneyException {
        final User user = bookingFacade.createUser(new UserEntity("Andrii", "Test"));
        final Event event = bookingFacade.createEvent(new EventEntity("Event", new Date(), 100));
        bookingFacade.refillUser(event.getTicketPrice() - 10, user.getId());
        final Ticket ticket = bookingFacade.bookTicket(user.getId(), event.getId(), 4, Ticket.Category.PREMIUM);
    }

    @Test(expected = IllegalArgumentException.class)
    public void refill_for_negative_amount_should_throw_an_exception() {
        final User user = bookingFacade.createUser(new UserEntity("Andrii", "Test"));
        bookingFacade.refillUser(-500, user.getId());
    }

}
