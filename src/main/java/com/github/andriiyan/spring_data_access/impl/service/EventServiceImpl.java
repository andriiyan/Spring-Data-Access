package com.github.andriiyan.spring_data_access.impl.service;

import com.github.andriiyan.spring_data_access.api.dao.EventDao;
import com.github.andriiyan.spring_data_access.api.model.Event;
import com.github.andriiyan.spring_data_access.api.service.EventService;
import com.github.andriiyan.spring_data_access.impl.dao.exception.ModelNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

class EventServiceImpl implements EventService {

    private static final Logger logger = LoggerFactory.getLogger(EventServiceImpl.class);

    @Autowired
    private EventDao eventDao;

    @Override
    public Event getEventById(long eventId) {
        final Event event = eventDao.findById(eventId);
        logger.debug("getEventById was invoked with userId={} and returning {}", eventId, event);
        return event;
    }

    @Override
    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        final List<Event> events = eventDao.getEventsByTitle(title, pageSize, pageNum);
        logger.debug("getEventsByTitle was invoked with title={}, pageSize={}, pageNum={} and returning {}", title, pageSize, pageNum, events);
        return events;
    }

    @Override
    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
        final List<Event> events = eventDao.getEventsForDay(day, pageSize, pageNum);
        logger.debug("getEventsForDay was invoked with day={}, pageSize={}, pageNum={} and returning {}", day, pageSize, pageNum, events);
        return events;
    }

    @Override
    public Event createEvent(Event event) {
        final Event mEvent = eventDao.save(event);
        logger.debug("createEvent was invoked with event={} and returning {}", event, mEvent);
        return mEvent;
    }

    @Override
    public Event updateEvent(Event event) throws ModelNotFoundException {
        final Event mEvent = eventDao.update(event);
        logger.debug("updateEvent was invoked with event={} and returning {}", event, mEvent);
        return mEvent;
    }

    @Override
    public boolean deleteEvent(long eventId) {
        final boolean result = eventDao.delete(eventId);
        logger.debug("deleteEvent was invoked with eventId={} and returning {}", eventId, result);
        return result;
    }

    public void setEventDao(EventDao eventDao) {
        this.eventDao = eventDao;
    }
}
