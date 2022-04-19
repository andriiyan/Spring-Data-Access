package com.github.andriiyan.spring_data_access;

import com.github.andriiyan.spring_data_access.api.model.Ticket;
import com.github.andriiyan.spring_data_access.api.model.User;
import com.github.andriiyan.spring_data_access.impl.model.UserEntity;
import org.junit.Assert;
import org.junit.Test;

public class FacadeIntegrationTest extends BaseContainerTest {

    @Test
    public void facade_should_write_data_into_the_storage() {
        final User user = bookingFacade.createUser(new UserEntity(0, "Andrii", "Test"));
        final Ticket ticket = bookingFacade.bookTicket(user.getId(), 3, 4, Ticket.Category.PREMIUM);
        Assert.assertEquals(user, userDao.findAll().toArray()[0]);
        Assert.assertEquals(ticket, ticketDao.findAll().toArray()[0]);
        Assert.assertEquals(ticket, bookingFacade.getBookedTickets(user, 1, 0).toArray()[0]);
    }

    @Test
    public void user_id_should_change_after_save_delete_save() {
        User user = bookingFacade.createUser(new UserEntity(0, "Andrii", "Test"));
        Assert.assertEquals(1, user.getId());
        Assert.assertEquals(user, bookingFacade.getUserById(user.getId()));
        Assert.assertTrue(bookingFacade.deleteUser(user.getId()));
        Assert.assertNull(bookingFacade.getUserById(user.getId()));
        user = bookingFacade.createUser(new UserEntity(0, "Andrii", "Test"));
        Assert.assertEquals(2, user.getId());
        Assert.assertEquals(user, bookingFacade.getUserById(user.getId()));
        Assert.assertTrue(bookingFacade.deleteUser(user.getId()));
        Assert.assertNull(bookingFacade.getUserById(user.getId()));
    }

}
