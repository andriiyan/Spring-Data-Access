package com.github.andriiyan.spring_data_access.impl.model;

import com.github.andriiyan.spring_data_access.api.model.Event;
import com.github.andriiyan.spring_data_access.impl.utils.DateUtils;
import com.github.andriiyan.spring_data_access.impl.utils.JsonInstanceCreator;
import com.google.gson.Gson;
import jakarta.persistence.*;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(
        name = "event",
        uniqueConstraints = @UniqueConstraint(columnNames = {"title", "date"})
)
public class EventEntity implements Event {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "date", nullable = false)
    private String dateString;

    @Column(name = "ticket_price", nullable = false)
    private double ticketPrice;

    @Transient
    private ZonedDateTime date;

    public EventEntity(long id, String title, ZonedDateTime date) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.dateString = date.format(DateUtils.dbDateTimeFormatter);
    }

    public EventEntity(long id, String title, ZonedDateTime date, double ticketPrice) {
        this(id, title, date);
        this.ticketPrice = ticketPrice;
    }

    public EventEntity(long id, String title, String date) {
        this.id = id;
        this.title = title;
        this.dateString = date;
        this.date = ZonedDateTime.from(DateUtils.dbDateTimeFormatter.parse(date));
    }

    public EventEntity(long id, String title, String date, double ticketPrice) {
        this(id, title, date);
        this.ticketPrice = ticketPrice;
    }

    public EventEntity() {
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public Date getDate() {
        return DateUtils.getDateFromZonedDateTime(date);
    }

    @Override
    public void setDate(Date date) {
        this.date = DateUtils.fromDate(date);
        this.dateString = DateUtils.dbDateTimeFormatter.format(this.date);
    }

    @Override
    public double getTicketPrice() {
        return ticketPrice;
    }

    @Override
    public void setTicketPrice(double price) {
        this.ticketPrice = price;
    }

    @Override
    public String toString() {
        return "EventImpl{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", date=" + date +
                '}';
    }

    public static class EventJsonInstanceCreator implements JsonInstanceCreator<Event> {

        @Override
        public Collection<Event> createInstances(String source, Gson gson) {
            return Arrays.asList(gson.fromJson(source, EventEntity[].class));
        }

        @Override
        public Class<Event> getType() {
            return Event.class;
        }
    }
}
