package org.example.booking_project.service;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface EventService {
    public void getEventFromQueue();
    public void eventToDatabase(String event)throws JsonProcessingException;

}
