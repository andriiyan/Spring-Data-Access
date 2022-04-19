package com.github.andriiyan.spring_data_access;

import com.github.andriiyan.spring_data_access.impl.utils.JsonInstanceCreator;
import com.github.andriiyan.spring_data_access.impl.utils.file.FileUtils;
import com.github.andriiyan.spring_data_access.impl.utils.file.Serializer;
import org.junit.Assert;
import org.junit.Test;

public class ContainerTest extends BaseContainerTest {

    @Test
    public void defaultConfig() {
        Assert.assertNotNull(eventService);
        Assert.assertNotNull(ticketService);
        Assert.assertNotNull(userService);
        Assert.assertNotNull(bookingFacade);
        Assert.assertNotNull(eventDao);
        Assert.assertNotNull(ticketDao);
        Assert.assertNotNull(userDao);
        Assert.assertNotNull(eventDao);
        verifyNotContainsInConfig(context, JsonInstanceCreator.class);
        verifyNotContainsInConfig(context, FileUtils.class);
        verifyNotContainsInConfig(context, Serializer.class);
    }

    @Test
    public void jsonConfig() {
        setActiveProfiles("json");
        Assert.assertNotNull(getProperty("dao.event.initializationFilePath"));
        Assert.assertNotNull(getProperty("dao.ticket.initializationFilePath"));
        Assert.assertNotNull(getProperty("dao.user.initializationFilePath"));
        Assert.assertNotNull(getProperty("dump.itemCount"));
        Assert.assertNotNull(getProperty("dump.rootFolder"));
        Assert.assertNotNull(eventService);
        Assert.assertNotNull(ticketService);
        Assert.assertNotNull(userService);
        Assert.assertNotNull(bookingFacade);
        Assert.assertNotNull(eventDao);
        Assert.assertNotNull(ticketDao);
        Assert.assertNotNull(userDao);
        Assert.assertNotNull(eventDao);
        Assert.assertNotNull(context.getBeansOfType(JsonInstanceCreator.class));
        Assert.assertNotNull(context.getBean(FileUtils.class));
        Assert.assertNotNull(context.getBean(Serializer.class));
    }

    @Test
    public void jsonDumpConfig() {
        setActiveProfiles("json", "dump");
        Assert.assertNotNull(getProperty("dao.event.initializationFilePath"));
        Assert.assertNotNull(getProperty("dao.ticket.initializationFilePath"));
        Assert.assertNotNull(getProperty("dao.user.initializationFilePath"));
        Assert.assertNotNull(getProperty("dump.itemCount"));
        Assert.assertNotNull(getProperty("dump.rootFolder"));
        Assert.assertNotNull(eventService);
        Assert.assertNotNull(ticketService);
        Assert.assertNotNull(userService);
        Assert.assertNotNull(bookingFacade);
        Assert.assertNotNull(eventDao);
        Assert.assertNotNull(ticketDao);
        Assert.assertNotNull(userDao);
        Assert.assertNotNull(eventDao);
        Assert.assertNotNull(context.getBeansOfType(JsonInstanceCreator.class));
        Assert.assertNotNull(context.getBean(FileUtils.class));
        Assert.assertNotNull(context.getBean(Serializer.class));
    }

    @Test
    public void byteConfig() {
        setActiveProfiles("byte");
        Assert.assertNotNull(getProperty("dao.event.initializationFilePath"));
        Assert.assertNotNull(getProperty("dao.ticket.initializationFilePath"));
        Assert.assertNotNull(getProperty("dao.user.initializationFilePath"));
        Assert.assertNotNull(getProperty("dump.itemCount"));
        Assert.assertNotNull(getProperty("dump.rootFolder"));
        Assert.assertNotNull(eventService);
        Assert.assertNotNull(ticketService);
        Assert.assertNotNull(userService);
        Assert.assertNotNull(bookingFacade);
        Assert.assertNotNull(eventDao);
        Assert.assertNotNull(ticketDao);
        Assert.assertNotNull(userDao);
        Assert.assertNotNull(eventDao);
        verifyNotContainsInConfig(context, JsonInstanceCreator.class);
        Assert.assertNotNull(context.getBean(FileUtils.class));
        Assert.assertNotNull(context.getBean(Serializer.class));
    }

    @Test
    public void byteDumpConfig() {
        setActiveProfiles("byte", "dump");
        Assert.assertNotNull(getProperty("dao.event.initializationFilePath"));
        Assert.assertNotNull(getProperty("dao.ticket.initializationFilePath"));
        Assert.assertNotNull(getProperty("dao.user.initializationFilePath"));
        Assert.assertNotNull(getProperty("dump.itemCount"));
        Assert.assertNotNull(getProperty("dump.rootFolder"));
        Assert.assertNotNull(eventService);
        Assert.assertNotNull(ticketService);
        Assert.assertNotNull(userService);
        Assert.assertNotNull(bookingFacade);
        Assert.assertNotNull(eventDao);
        Assert.assertNotNull(ticketDao);
        Assert.assertNotNull(userDao);
        Assert.assertNotNull(eventDao);
        verifyNotContainsInConfig(context, JsonInstanceCreator.class);
        Assert.assertNotNull(context.getBean(FileUtils.class));
        Assert.assertNotNull(context.getBean(Serializer.class));
    }

}
