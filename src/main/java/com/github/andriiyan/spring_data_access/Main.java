package com.github.andriiyan.spring_data_access;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/*
 * Pass the `spring.profiles.active=local` into the environment variable to run on the h2 in-memory database.
 */
public class Main {

    public static final String[] configurations = new String[] {
            "application-local.xml",
            "application.xml"
    };

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext(configurations);
        logger.info("Container has been configured");
        // TODO application code
        logger.info("App finishes it work");
    }

}
