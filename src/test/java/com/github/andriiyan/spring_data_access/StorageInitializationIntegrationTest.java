package com.github.andriiyan.spring_data_access;

import com.github.andriiyan.spring_data_access.api.model.Event;
import com.github.andriiyan.spring_data_access.api.model.Ticket;
import com.github.andriiyan.spring_data_access.api.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;

public class StorageInitializationIntegrationTest extends BaseContainerTest {

    private void testDumpUsingConfig(@NonNull final String config) {
        setActiveProfiles(config, "dump");
        refreshBeans();
        final List<User> users = new ArrayList<>(userStorage.findAll());
        final List<Ticket> tickets = new ArrayList<>(ticketStorage.findAll());
        final List<Event> events = new ArrayList<>(eventStorage.findAll());
        final int itemCount = Integer.parseInt(getProperty("dump.itemCount"));
        Assert.assertEquals(itemCount, users.size());
        Assert.assertEquals(itemCount, tickets.size());
        Assert.assertEquals(itemCount, events.size());
    }

    @Test
    public void byteSerializer_storageShouldInitializeFromTheFile() {
        testDumpUsingConfig("byte");
    }

    @Test
    public void jsonSerializer_storageShouldInitializeFromTheFile() {
        testDumpUsingConfig("json");
    }


}
