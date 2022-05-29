package com.github.andriiyan.spring_data_access;

import org.junit.Assert;
import org.junit.Test;

public class ContainerTest {

    @Test
    public void defaultConfig() {
        BaseContainerTest containerTest = new BaseContainerTest();
        Assert.assertEquals("org.hibernate.dialect.PostgreSQLDialect", containerTest.getProperty("hibernate.dialect"));
        Assert.assertEquals("true", containerTest.getProperty("hibernate.show-sql"));
        Assert.assertEquals("update", containerTest.getProperty("hibernate.hbm2ddl.auto"));
        Assert.assertNotNull(containerTest.eventService);
        Assert.assertNotNull(containerTest.ticketService);
        Assert.assertNotNull(containerTest.userService);
        Assert.assertNotNull(containerTest.bookingFacade);
        Assert.assertNotNull(containerTest.eventDao);
        Assert.assertNotNull(containerTest.ticketDao);
        Assert.assertNotNull(containerTest.userDao);
        Assert.assertNotNull(containerTest.eventDao);
    }

    @Test
    public void localConfig() {
        BaseContainerTest containerTest = new BaseContainerTest("local", "dump");
        Assert.assertEquals("org.hibernate.dialect.H2Dialect", containerTest.getProperty("hibernate.dialect"));
        Assert.assertEquals("true", containerTest.getProperty("hibernate.show-sql"));
        Assert.assertEquals("create", containerTest.getProperty("hibernate.hbm2ddl.auto"));
        Assert.assertEquals("jdbc:h2:mem:myDb;DB_CLOSE_DELAY=-1", containerTest.getProperty("spring.datasource.url"));
        Assert.assertEquals("spring_data_access", containerTest.getProperty("spring.datasource.username"));
        Assert.assertEquals("SuperPassword1!", containerTest.getProperty("spring.datasource.password"));
        Assert.assertEquals("org.h2.Driver", containerTest.getProperty("spring.datasource.driver-class-name"));
        Assert.assertTrue(containerTest.isContainsProperty("initial-date-population.enabled"));
        Assert.assertTrue(containerTest.isContainsProperty("initial-date-population.read"));
        Assert.assertTrue(containerTest.isContainsProperty("initial-date-population.item_count"));
        Assert.assertTrue(containerTest.isContainsProperty("initial-date-population.user_file_path"));
        Assert.assertTrue(containerTest.isContainsProperty("initial-date-population.user_account_file_path"));
        Assert.assertTrue(containerTest.isContainsProperty("initial-date-population.ticket_file_path"));
        Assert.assertTrue(containerTest.isContainsProperty("initial-date-population.event_file_path"));
        Assert.assertNotNull(containerTest.eventService);
        Assert.assertNotNull(containerTest.ticketService);
        Assert.assertNotNull(containerTest.userService);
        Assert.assertNotNull(containerTest.bookingFacade);
        Assert.assertNotNull(containerTest.eventDao);
        Assert.assertNotNull(containerTest.ticketDao);
        Assert.assertNotNull(containerTest.userDao);
        Assert.assertNotNull(containerTest.eventDao);
    }

}
