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

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "application.xml",
                "application-byte.xml",
                "application-json.xml",
                "application-dump.xml"
                );
        logger.info("Container has been configured");
        final EventService eventService = context.getBean(EventService.class);
        final TicketService ticketService = context.getBean(TicketService.class);
        final UserService userService = context.getBean(UserService.class);
        final BookingFacade bookingFacade = context.getBean(BookingFacade.class);

        logger.info("App finishes it work");
    }

}
