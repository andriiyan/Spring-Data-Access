package com.github.andriiyan.spring_data_access;

import com.github.andriiyan.spring_data_access.impl.storage.hibernate.HibernateConfiguration;

public class TestConfiguration {

    protected HibernateConfiguration configuration = new HibernateConfiguration("hibernate-local.cfg.xml");

}
