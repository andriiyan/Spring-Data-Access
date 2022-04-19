package com.github.andriiyan.spring_data_access.api.dao;

import com.github.andriiyan.spring_data_access.api.model.Event;
import com.github.andriiyan.spring_data_access.api.model.Ticket;
import com.github.andriiyan.spring_data_access.api.model.User;

import java.util.List;

public interface TicketDao extends BaseDao<Ticket> {

    List<Ticket> getBookedTickets(User user, int pageSize, int pageNum);

    List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum);

}
