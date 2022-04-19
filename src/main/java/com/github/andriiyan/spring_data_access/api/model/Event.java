package com.github.andriiyan.spring_data_access.api.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by maksym_govorischev.
 */
public interface Event extends Identifierable, Serializable {
    /**
     * Event id. UNIQUE.
     * @return Event Id
     */
    long getId();
    void setId(long id);
    String getTitle();
    void setTitle(String title);
    Date getDate();
    void setDate(Date date);
}
