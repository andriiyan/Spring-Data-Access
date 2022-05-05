package com.github.andriiyan.spring_data_access.impl.storage.hibernate;


import com.github.andriiyan.spring_data_access.impl.model.EventEntity;
import com.github.andriiyan.spring_data_access.impl.model.TicketEntity;
import com.github.andriiyan.spring_data_access.impl.model.UserAccountEntity;
import com.github.andriiyan.spring_data_access.impl.model.UserEntity;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;

public class HibernateConfiguration implements ISessionFactoryProvider {

    private final ServiceRegistry serviceRegistry;
    private final SessionFactory sessionFactory;

    public HibernateConfiguration(@NonNull String configurationFileName) {
        Configuration configuration = new Configuration().configure(configurationFileName)
                .addAnnotatedClass(EventEntity.class)
                .addAnnotatedClass(UserEntity.class)
                .addAnnotatedClass(TicketEntity.class)
                .addAnnotatedClass(UserAccountEntity.class);
        serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }

    @Override
    @NonNull
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    private void close() {
        sessionFactory.close();
        serviceRegistry.close();
    }

}
