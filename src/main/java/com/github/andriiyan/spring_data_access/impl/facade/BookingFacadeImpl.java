package com.github.andriiyan.spring_data_access.impl.facade;

import com.github.andriiyan.spring_data_access.api.facade.BookingFacade;
import com.github.andriiyan.spring_data_access.api.model.Event;
import com.github.andriiyan.spring_data_access.api.model.Ticket;
import com.github.andriiyan.spring_data_access.api.model.User;
import com.github.andriiyan.spring_data_access.api.service.EventService;
import com.github.andriiyan.spring_data_access.api.service.TicketService;
import com.github.andriiyan.spring_data_access.api.service.UserService;
import com.github.andriiyan.spring_data_access.impl.dao.exception.ModelNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

class BookingFacadeImpl implements BookingFacade {

    private static final Logger logger = LoggerFactory.getLogger(BookingFacadeImpl.class);

    private final EventService eventService;
    private final TicketService ticketService;
    private final UserService userService;

    public BookingFacadeImpl(EventService eventService, TicketService ticketService, UserService userService) {
        this.eventService = eventService;
        this.ticketService = ticketService;
        this.userService = userService;
    }

    @Override
    public Event getEventById(long eventId) {
        final Event event = eventService.getEventById(eventId);
        logger.debug("getEventById invoked with {} returning {}", eventId, event);
        return event;
    }

    @Override
    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        final List<Event> events = eventService.getEventsByTitle(title, pageSize, pageNum);
        logger.debug("getEventsByTitle invoked with title={}, pageSize={}, pageNum={} and returned {}", title, pageSize, pageNum, events);
        return events;
    }

    @Override
    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
        final List<Event> events = eventService.getEventsForDay(day, pageSize, pageNum);
        logger.debug("getEventsByTitle invoked with day={}, pageSize={}, pageNum={} and returned {}", day, pageSize, pageNum, events);
        return events;
    }

    @Override
    public Event createEvent(Event event) {
        final Event mEvent = eventService.createEvent(event);
        logger.debug("createEvent was invoked with {} and returning {}", event, mEvent);
        return mEvent;
    }

    @Override
    public Event updateEvent(Event event) throws ModelNotFoundException {
        final Event mEvent = eventService.updateEvent(event);
        logger.debug("updateEvent was invoked with {} and returning {}", event, mEvent);
        return mEvent;
    }

    @Override
    public boolean deleteEvent(long eventId) {
        final boolean result = eventService.deleteEvent(eventId);
        logger.debug("deleteEvent was invoked with {} and returning {}", eventId, result);
        return result;
    }

    @Override
    public User getUserById(long userId) {
        final User user = userService.getUserById(userId);
        logger.debug("getUserById was invoked with {} and returning {}", userId, user);
        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        final User user = userService.getUserByEmail(email);
        logger.debug("getUserByEmail was invoked with {} and returning {}", email, user);
        return user;
    }

    @Override
    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        final List<User> users = userService.getUsersByName(name, pageSize, pageNum);
        logger.debug("getUsersByName was invoked with name={}, pageSize={}, pageNum={} and returning {}", name, pageSize, pageNum, users);
        return users;
    }

    @Override
    public User createUser(User user) {
        final User mUser = userService.createUser(user);
        logger.debug("createUser was invoked with {} and returning {}", user, mUser);
        return mUser;
    }

    @Override
    public User updateUser(User user) throws ModelNotFoundException {
        final User mUser = userService.updateUser(user);
        logger.debug("updateUser was invoked with {} and returning {}", user, mUser);
        return mUser;
    }

    @Override
    public boolean deleteUser(long userId) {
        final boolean result = userService.deleteUser(userId);
        logger.debug("deleteUser was invoked with {} and returning {}", userId, result);
        return result;
    }

    @Override
    public Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category) {
        final Ticket ticket = ticketService.bookTicket(userId, eventId, place, category);
        logger.debug("bookTicket was invoked with userId={}, eventId={}, place={}, category={} and returning {}", userId, eventId, place, category, ticket);
        return ticket;
    }

    @Override
    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
        final List<Ticket> tickets = ticketService.getBookedTickets(user, pageSize, pageNum);
        logger.debug("getBookedTickets was invoked with user={}, pageSize={}, pageNum={} and returning {}", user, pageSize, pageNum, tickets);
        return tickets;
    }

    @Override
    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        final List<Ticket> tickets = ticketService.getBookedTickets(event, pageSize, pageNum);
        logger.debug("getBookedTickets was invoked with event={}, pageSize={}, pageNum={} and returning {}", event, pageSize, pageNum, tickets);
        return tickets;
    }

    @Override
    public boolean cancelTicket(long ticketId) {
        final boolean result = ticketService.cancelTicket(ticketId);
        logger.debug("cancelTicket was invoked with {} and returning {}", ticketId, result);
        return result;
    }
}
