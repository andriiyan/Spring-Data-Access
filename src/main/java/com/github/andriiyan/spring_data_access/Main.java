package com.github.andriiyan.spring_data_access;

import com.github.andriiyan.spring_data_access.api.facade.BookingFacade;
import com.github.andriiyan.spring_data_access.api.service.EventService;
import com.github.andriiyan.spring_data_access.api.service.TicketService;
import com.github.andriiyan.spring_data_access.api.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    /*
     * Pass the `spring.profiles.active=local` into the environment variable to run on the h2 in-memory database.
     * Default configuration runs on the PostgreSQL database, see hibernate.cfg.xml for more details.
     */

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "application.xml",
                "application-local.xml"
                );
        logger.info("Container has been configured");
        final EventService eventService = context.getBean(EventService.class);
        final TicketService ticketService = context.getBean(TicketService.class);
        final UserService userService = context.getBean(UserService.class);
        final BookingFacade bookingFacade = context.getBean(BookingFacade.class);

//        Event ev = eventService.createEvent(new EventEntity("EPAM learning program", new Date(), 10));
//        logger.debug("Saved event: {}", ev);
//        Event event = eventService.getEventById(ev.getId());
//        logger.debug("Found event: {}", event);
//        User usr = userService.createUser(new UserEntity("Andrii", "Andrii_Yan@epam.com"));
//        logger.debug("Created user: {}", usr);
//        User user = userService.getUserById(usr.getId());
//        logger.debug("Found user: {}", user);
//        user.setName("New email");
//        User updatedUser = userService.updateUser(user);
//        logger.debug("Modified user: {}", updatedUser);
//
//        Ticket ticket = ticketService.bookTicket(updatedUser.getId(), event.getId(), 505, Ticket.Category.PREMIUM);
//        logger.debug("Bocked ticker: {}", ticket);
//
//        List<Event> events = bookingFacade.getEventsForDay(new Date(), 10, 0);

        logger.info("App finishes it work");
    }

}
