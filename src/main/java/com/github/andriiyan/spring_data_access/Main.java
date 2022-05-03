package com.github.andriiyan.spring_data_access;

import com.github.andriiyan.spring_data_access.api.facade.BookingFacade;
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
        final BookingFacade bookingFacade = context.getBean(BookingFacade.class);

//        final User user = bookingFacade.getUserById(1);
//        final Event event = bookingFacade.getEventById(13);
//        bookingFacade.refillUser(500, user.getId());
//        try {
//            final Ticket ticket = bookingFacade.bookTicket(user.getId(), event.getId(), 55, Ticket.Category.BAR);
//            final List<Ticket> ticketList = bookingFacade.getBookedTickets(user, 100, 0);
//            boolean debug = true;
//        } catch (NotEnoughMoneyException e) {
//            throw new RuntimeException(e);
//        }

        logger.info("App finishes it work");
    }

}
