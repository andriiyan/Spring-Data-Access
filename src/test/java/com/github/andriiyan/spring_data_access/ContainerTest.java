package com.github.andriiyan.spring_data_access;

import org.junit.Assert;
import org.junit.Test;

public class ContainerTest extends BaseContainerTest {

    @Test
    public void defaultConfig() {
        Assert.assertEquals("org.hibernate.dialect.PostgreSQLDialect", getProperty("hibernate.dialect"));
        Assert.assertEquals("true", getProperty("hibernate.show-sql"));
        Assert.assertEquals("update", getProperty("hibernate.hbm2ddl.auto"));
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
        Assert.assertEquals("org.hibernate.dialect.H2Dialect", getProperty("hibernate.dialect"));
        Assert.assertEquals("true", getProperty("hibernate.show-sql"));
        Assert.assertEquals("create", getProperty("hibernate.hbm2ddl.auto"));
        Assert.assertEquals("jdbc:h2:mem:myDb;DB_CLOSE_DELAY=-1", getProperty("spring.datasource.url"));
        Assert.assertEquals("spring_data_access", getProperty("spring.datasource.username"));
        Assert.assertEquals("SuperPassword1!", getProperty("spring.datasource.password"));
        Assert.assertEquals("org.h2.Driver", getProperty("spring.datasource.driver-class-name"));
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
