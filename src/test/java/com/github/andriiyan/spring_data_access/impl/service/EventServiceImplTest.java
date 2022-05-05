package com.github.andriiyan.spring_data_access.impl.service;

import com.github.andriiyan.spring_data_access.api.dao.EventDao;
import com.github.andriiyan.spring_data_access.api.model.Event;
import com.github.andriiyan.spring_data_access.impl.model.EventEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class EventServiceImplTest {

    @Mock
    private EventDao eventDao;

    @InjectMocks
    private EventServiceImpl eventService;

    @Test
    public void getEventById_shouldReturnSameModelAsDao() {
        final long eventId = 100;
        final Optional<Event> returningEvent = Optional.of(new EventEntity());
        Mockito.when(eventDao.findById(eventId)).thenReturn(returningEvent);

        final Event returnedEvent = eventService.getEventById(eventId);
        Assert.assertEquals(returningEvent.get(), returnedEvent);
        Mockito.verify(eventDao).findById(eventId);
    }

    @Test
    public void getEventsByTitle_shouldReturnCorrectAnswer() {
        final String title = "test title";
        final int pageSize = 3;
        final int pageNum = 1;

        // generating events for the 5 pages
        final List<Event> returningEvents = new ArrayList<>();
        for (int i = 0; i < 5 * pageSize; i++) {
            returningEvents.add(new EventEntity("name " + i, new Date(), i));
        }

        Mockito.when(eventDao.getEventsByTitle(title, pageSize, pageNum)).thenReturn(returningEvents);
        final List<Event> returnedEvents = eventService.getEventsByTitle(title, pageSize, pageNum);

        Assert.assertEquals(returningEvents, returnedEvents);

        Mockito.verify(eventDao).getEventsByTitle(title, pageSize, pageNum);
    }

    @Test
    public void getEventsForDay_shouldReturnCorrectAnswer() {
        final Date date = new Date() ;
        final int pageSize = 3;
        final int pageNum = 1;

        // generating events for the 5 pages
        final List<Event> returningEvents = new ArrayList<>();
        for (int i = 0; i < 5 * pageSize; i++) {
            returningEvents.add(new EventEntity("name " + i, new Date(), i));
        }

        Mockito.when(eventDao.getEventsForDay(date, pageSize, pageNum)).thenReturn(returningEvents);
        final List<Event> returnedEvents = eventService.getEventsForDay(date, pageSize, pageNum);

        Assert.assertEquals(returningEvents, returnedEvents);

        Mockito.verify(eventDao).getEventsForDay(date, pageSize, pageNum);
    }

    @Test
    public void createEvent_shouldReturnSameModelAsDao() {
        final Event savingEvent = new EventEntity("name", new Date(), 100);
        final Event returningEvent = new EventEntity("test", new Date(), 50);
        Mockito.when(eventDao.save(savingEvent)).thenReturn(returningEvent);

        final Event returnedEvent = eventService.createEvent(savingEvent);
        Assert.assertEquals(returningEvent, returnedEvent);
        Mockito.verify(eventDao).save(savingEvent);
    }

    @Test
    public void updateEvent_shouldReturnSameModelAsDao() {
        final Event savingEvent = new EventEntity("name", new Date(), 100);
        final Event returningEvent = new EventEntity("test", new Date(), 50);
        Mockito.when(eventDao.save(savingEvent)).thenReturn(returningEvent);

        final Event returnedEvent = eventService.updateEvent(savingEvent);
        Assert.assertEquals(returningEvent, returnedEvent);
        Mockito.verify(eventDao).save(savingEvent);
    }

    @Test
    public void delete_shouldReturnSameModelAsDao() {
        final long eventId = 100;
        Mockito.doNothing().when(eventDao).deleteById(eventId);

        Assert.assertTrue(eventService.deleteEvent(eventId));
        Mockito.verify(eventDao).deleteById(eventId);
    }

}