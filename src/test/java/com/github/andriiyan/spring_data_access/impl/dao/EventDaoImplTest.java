package com.github.andriiyan.spring_data_access.impl.dao;

import com.github.andriiyan.spring_data_access.api.model.Event;
import com.github.andriiyan.spring_data_access.TestConfiguration;
import com.github.andriiyan.spring_data_access.impl.model.EventEntity;
import org.junit.Assert;
import org.junit.Test;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventDaoImplTest extends TestConfiguration {

    private final EventDaoImpl eventDao = new EventDaoImpl(configuration);

    private void assertEvents(final List<Event> expectedEventEntities, final List<Event> eventEntities) {
        int index = 0;
        for (Event event : expectedEventEntities) {
            Event returnedEvent = eventEntities.get(index);
            Assert.assertEquals(event.getId(), returnedEvent.getId());
            Assert.assertEquals(event.getTitle(), returnedEvent.getTitle());
            Assert.assertEquals(event.getTicketPrice(), returnedEvent.getTicketPrice(), 0.01);
            index++;
        }
    }

    @Test
    public void getEventsByTitle_shouldReturnCorrectAnswer() {
        final String title = "title";
        final int pageSize = 5;
        final int pageNum = 1;

        List<Event> eventsToSave = new ArrayList<>();
        for (int i = 0; i < (pageNum + 1) * pageSize; i++) {
            Event event = new EventEntity("title #" + i, new Date(), i * 10);
            eventsToSave.add(eventDao.save(event));
        }

        final List<Event> returnedEvents = eventDao.getEventsByTitle(title, pageSize, pageNum);

        returnedEvents.forEach(event -> Assert.assertTrue(event.getTitle().contains(title)));

        assertEvents(
                eventsToSave.subList(pageSize * pageNum, pageSize * pageNum + pageSize),
                returnedEvents
        );
    }

    @Test
    public void getEventsForDay_shouldReturnCorrectAnswer() {
        Date date = new Date();
        final int pageSize = 3;
        final int pageNum = 1;

        final List<Event> eventWithSameDate = new ArrayList<>();

        for (int i = 0; i < (pageNum + 1) * pageSize; i++) {
            // saving some random events to have in DB not only suitable data
            eventDao.save(new EventEntity("title test #" + i, Date.from(ZonedDateTime.now().plusDays(1).toInstant()), i * 100));
            // save events that fit the query
            eventWithSameDate.add(eventDao.save(new EventEntity("title #" + i, new Date(), i * 10)));
        }

        final List<Event> returnedEvents = eventDao.getEventsForDay(date, pageSize, pageNum);

        assertEvents(
                eventWithSameDate.subList(pageSize * pageNum, pageSize * pageNum + pageSize),
                returnedEvents
        );
    }

}