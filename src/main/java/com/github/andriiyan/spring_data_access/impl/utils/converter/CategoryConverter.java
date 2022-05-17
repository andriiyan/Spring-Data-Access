package com.github.andriiyan.spring_data_access.impl.utils.converter;

import com.github.andriiyan.spring_data_access.api.model.Ticket;
import javax.persistence.AttributeConverter;

public class CategoryConverter implements AttributeConverter<Ticket.Category, String> {
    @Override
    public String convertToDatabaseColumn(Ticket.Category category) {
        return category.name();
    }

    @Override
    public Ticket.Category convertToEntityAttribute(String s) {
        return Ticket.Category.valueOf(s);
    }
}
