package com.github.andriiyan.spring_data_access;

import org.junit.Assert;
import org.junit.Test;

public class ContainerTest extends BaseContainerTest {

    @Test
    public void defaultConfig() {
        Assert.assertEquals("hibernate.cfg.xml", getProperty("hibernate.config.fileName"));
        Assert.assertNotNull(eventService);
        Assert.assertNotNull(ticketService);
        Assert.assertNotNull(userService);
        Assert.assertNotNull(bookingFacade);
        Assert.assertNotNull(eventDao);
        Assert.assertNotNull(ticketDao);
        Assert.assertNotNull(userDao);
        Assert.assertNotNull(eventDao);
    }

    @Test
    public void localConfig() {
        setActiveProfiles("local");
        Assert.assertEquals("hibernate-local.cfg.xml", getProperty("hibernate.config.fileName"));
        Assert.assertNotNull(eventService);
        Assert.assertNotNull(ticketService);
        Assert.assertNotNull(userService);
        Assert.assertNotNull(bookingFacade);
        Assert.assertNotNull(eventDao);
        Assert.assertNotNull(ticketDao);
        Assert.assertNotNull(userDao);
        Assert.assertNotNull(eventDao);
    }

}
