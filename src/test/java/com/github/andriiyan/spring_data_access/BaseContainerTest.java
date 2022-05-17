package com.github.andriiyan.spring_data_access;

import com.github.andriiyan.spring_data_access.api.dao.EventDao;
import com.github.andriiyan.spring_data_access.api.dao.TicketDao;
import com.github.andriiyan.spring_data_access.api.dao.UserAccountDao;
import com.github.andriiyan.spring_data_access.api.dao.UserDao;
import com.github.andriiyan.spring_data_access.api.facade.BookingFacade;
import com.github.andriiyan.spring_data_access.api.service.EventService;
import com.github.andriiyan.spring_data_access.api.service.TicketService;
import com.github.andriiyan.spring_data_access.api.service.UserAccountService;
import com.github.andriiyan.spring_data_access.api.service.UserService;
import com.github.andriiyan.spring_data_access.impl.utils.ListUtils;
import org.junit.Assert;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.lang.NonNull;

import java.util.Arrays;

public abstract class BaseContainerTest {

    private String[] profiles;

    public BaseContainerTest(String ... profiles) {
        super();
        this.profiles = profiles;
        initialize();
    }

    public BaseContainerTest() {
        super();
        initialize();
    }

    protected ConfigurableApplicationContext context;
    protected EventDao eventDao;
    protected TicketDao ticketDao;
    protected UserDao userDao;
    protected UserAccountDao userAccountDao;
    protected EventService eventService;
    protected TicketService ticketService;
    protected UserService userService;
    protected UserAccountService userAccountService;
    protected BookingFacade bookingFacade;

    protected ConfigurableApplicationContext getConfiguredContext() {
        if (profiles != null) {
            System.setProperty("spring.profiles.active", ListUtils.joinToString(Arrays.asList(profiles), ','));
        }
        return new ClassPathXmlApplicationContext(
                "application-local.xml",
                "application.xml"
        );
    }

    protected <T> void verifyNotContainsInConfig(ApplicationContext context, Class<T> type) {
        try {
            final T model = context.getBean(type);
            Assert.fail(type + " should not be present in the default config");
        } catch (NoSuchBeanDefinitionException ignored) {

        }
    }

    protected String getProperty(@NonNull String name) {
        return context.getBeanFactory().resolveEmbeddedValue("${" + name + "}");
    }

    private void initialize() {
        context = getConfiguredContext();
        refreshBeans();
    }

    private void refreshBeans() {
        eventDao = context.getBean(EventDao.class);
        ticketDao = context.getBean(TicketDao.class);
        userDao = context.getBean(UserDao.class);
        userAccountDao = context.getBean(UserAccountDao.class);
        eventService = context.getBean(EventService.class);
        ticketService = context.getBean(TicketService.class);
        userService = context.getBean(UserService.class);
        userAccountService = context.getBean(UserAccountService.class);
        bookingFacade = context.getBean(BookingFacade.class);
    }

    protected void setActiveProfiles(String ... profiles) {
        this.profiles = profiles;
        System.setProperty("spring.profiles.active", ListUtils.joinToString(Arrays.asList(profiles), ','));
        initialize();
    }

}
