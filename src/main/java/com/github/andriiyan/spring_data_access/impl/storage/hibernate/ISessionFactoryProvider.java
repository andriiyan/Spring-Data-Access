package com.github.andriiyan.spring_data_access.impl.storage.hibernate;

import org.hibernate.SessionFactory;
import org.springframework.lang.NonNull;

public interface ISessionFactoryProvider {
    @NonNull
    SessionFactory getSessionFactory();
}
