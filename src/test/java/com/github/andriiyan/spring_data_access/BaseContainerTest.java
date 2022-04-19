package com.github.andriiyan.spring_data_access;

import com.github.andriiyan.spring_data_access.api.dao.EventDao;
import com.github.andriiyan.spring_data_access.api.dao.TicketDao;
import com.github.andriiyan.spring_data_access.api.dao.UserDao;
import com.github.andriiyan.spring_data_access.api.facade.BookingFacade;
import com.github.andriiyan.spring_data_access.api.model.Event;
import com.github.andriiyan.spring_data_access.api.model.Ticket;
import com.github.andriiyan.spring_data_access.api.model.User;
import com.github.andriiyan.spring_data_access.api.service.EventService;
import com.github.andriiyan.spring_data_access.api.service.TicketService;
import com.github.andriiyan.spring_data_access.api.service.UserService;
import com.github.andriiyan.spring_data_access.api.storage.Storage;
import org.junit.Assert;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public abstract class BaseContainerTest {

    protected ConfigurableApplicationContext context;
    protected EventDao eventDao;
    protected TicketDao ticketDao;
    protected UserDao userDao;
    protected EventService eventService;
    protected TicketService ticketService;
    protected UserService userService;
    protected BookingFacade bookingFacade;
    protected Storage<Event> eventStorage;
    protected Storage<User> userStorage;
    protected Storage<Ticket> ticketStorage;

    protected ConfigurableApplicationContext getConfiguredContext() {
        return new ClassPathXmlApplicationContext(
                "application.xml",
                "application-byte.xml",
                "application-json.xml",
                "application-dump.xml"
        );
    }

    public BaseContainerTest() {
        super();
        initialize();
    }

    protected <T> void verifyNotContainsInConfig(ApplicationContext context, Class<T> type) {
        try {
            final T model = context.getBean(type);
            Assert.fail(type + " should not be present in the default config");
        } catch (NoSuchBeanDefinitionException ignored) {

        }
    }

    protected String getProperty(String name) {
        return context.getBeanFactory().resolveEmbeddedValue("${" + name + "}");
    }

    protected void initialize() {
        context = getConfiguredContext();
        refreshBeans();
    }

    protected void refreshBeans() {
        eventDao = context.getBean(EventDao.class);
        ticketDao = context.getBean(TicketDao.class);
        userDao = context.getBean(UserDao.class);
        eventService = context.getBean(EventService.class);
        ticketService = context.getBean(TicketService.class);
        userService = context.getBean(UserService.class);
        bookingFacade = context.getBean(BookingFacade.class);
        ticketStorage = context.getBean("ticketStorage", Storage.class);
        userStorage = context.getBean("userStorage", Storage.class);
        eventStorage = context.getBean("eventStorage", Storage.class);
    }

    protected void setActiveProfiles(String ...profiles) {
        context.getEnvironment().setActiveProfiles(profiles);
        context.refresh();
    }

}
