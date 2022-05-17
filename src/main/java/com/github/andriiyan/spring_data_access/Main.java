package com.github.andriiyan.spring_data_access;

import com.github.andriiyan.spring_data_access.api.exceptions.NotEnoughMoneyException;
import com.github.andriiyan.spring_data_access.api.facade.BookingFacade;
import com.github.andriiyan.spring_data_access.api.model.Event;
import com.github.andriiyan.spring_data_access.api.model.Ticket;
import com.github.andriiyan.spring_data_access.api.model.User;
import com.github.andriiyan.spring_data_access.impl.model.EventEntity;
import com.github.andriiyan.spring_data_access.impl.model.TicketEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;
import java.util.List;

/*
 * Pass the `spring.profiles.active=local` into the environment variable to run on the h2 in-memory database.
 */
public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "application-local.xml",
                "application.xml"
        );
        logger.info("Container has been configured");
        // TODO application code
        logger.info("App finishes it work");
    }

}
