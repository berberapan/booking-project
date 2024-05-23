package org.example.booking_project.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.booking_project.repos.EventRepo;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

public class EventServiceImplTests {

    private EventRepo EventRepo = mock(EventRepo.class);

    private EventServiceImpl sut;
    public void EventServiceImplTest(EventServiceImpl eventService){
        this.sut = eventService;
    }

    @Test
    void saveEventsShouldSaveCorrectly() throws JsonProcessingException {

     //   sut.eventToDatabase("EventServiceImplTestData.json");

    }

}
