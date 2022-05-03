package com.github.andriiyan.spring_data_access.impl.model;

import com.github.andriiyan.spring_data_access.api.model.Event;
import com.github.andriiyan.spring_data_access.impl.utils.DateUtils;
import jakarta.persistence.*;
import org.springframework.lang.NonNull;

import java.sql.Time;
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
    @Temporal(value = TemporalType.DATE)
    private java.sql.Date date;

    @Column(name = "time", nullable = false)
    @Temporal(value = TemporalType.TIME)
    private java.sql.Time time;

    @Column(name = "ticket_price", nullable = false)
    private double ticketPrice;

    @Transient
    private Date utcJavaDate;

    public EventEntity(@NonNull String title, @NonNull Date dateAndTime, double ticketPrice) {
        this(0, title, dateAndTime, ticketPrice);
    }

    public EventEntity(long id, @NonNull String title, @NonNull Date dateAndTime, double ticketPrice) {
        this.id = id;
        this.title = title;
        this.ticketPrice = ticketPrice;
        setDate(dateAndTime);
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
        return utcJavaDate;
    }

    @Override
    public void setDate(Date date) {
        this.utcJavaDate = DateUtils.toUTCDate(date);
        this.date = new java.sql.Date(utcJavaDate.getTime());
        this.time = new Time(utcJavaDate.getTime());
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

}
