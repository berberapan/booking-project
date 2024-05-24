package org.example.booking_project.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.booking_project.models.EventBase;
import org.example.booking_project.repos.EventRepo;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;

import static org.mockito.Mockito.*;

public class EventServiceImplTests {
    EventRepo eventRepo = mock(EventRepo.class);
    EventServiceImpl sut;

    @BeforeEach
    void setUp() {
        sut = new EventServiceImpl(eventRepo);
    }

    @Test
    void canMapEventBaseCorrectlyTests() throws JsonProcessingException {
        String jsonString = "{\"type\":\"RoomOpened\",\"TimeStamp\":\"2024-05-13T02:45:27.727446667\", \"RoomNo\":\"101\"}";
        LocalDateTime time = LocalDateTime.of(2024, 5, 13, 2, 45, 27, 727446667);
        sut.eventToDatabase(jsonString);

        verify(eventRepo).save(argThat(event -> Objects.equals(event.getType(), "RoomOpened")));
        verify(eventRepo).save(argThat(event -> Objects.equals(event.getTimeStamp(), time)));
    }

    @Test
    void canMapRoomOpenedCorrectlyTests() throws IOException {

        String jsonString = "{\"type\":\"RoomOpened\",\"TimeStamp\":\"2024-05-13T02:45:27.727446667\", \"RoomNo\":\"101\"}";
        sut.eventToDatabase(jsonString);

        verify(eventRepo).save(argThat(event -> Objects.equals(event.toString(), "RoomOpened(RoomNo=101)")));
    }

    @Test
    void canMapRoomClosedCorrectlyTests() throws IOException {

        String jsonString = "{\"type\":\"RoomClosed\",\"TimeStamp\":\"2024-05-13T02:45:27.727446667\", \"RoomNo\":\"102\"}";
        sut.eventToDatabase(jsonString);

        verify(eventRepo).save(argThat(event -> Objects.equals(event.toString(), "RoomClosed(RoomNo=102)")));
    }

    @Test
    void canMapCleaningStartedCorrectlyTests() throws IOException {

        String jsonString = "{\"type\":\"RoomCleaningStarted\",\"TimeStamp\":\"2024-06-13T02:40:27.727446667\", \"RoomNo\":\"103\",\"CleaningByUser\":\"Britt-Marie\"}";
        sut.eventToDatabase(jsonString);

        verify(eventRepo).save(argThat(event -> Objects.equals(event.toString(), "RoomCleaningStarted(RoomNo=103, CleaningByUser=Britt-Marie)")));
    }

    @Test
    void canMapRoomCleaningFinishedCorrectlyTest() throws IOException {

        String jsonString = "{\"type\":\"RoomCleaningFinished\",\"TimeStamp\":\"2024-06-13T02:55:27.727446667\",\"RoomNo\":\"104\",\"CleaningByUser\":\"Britt-Marie\"}";
        sut.eventToDatabase(jsonString);

        verify(eventRepo).save(argThat(event -> Objects.equals(event.toString(), "RoomCleaningFinished(RoomNo=104, CleaningByUser=Britt-Marie)")));
    }

    @Test
    void canSaveEventCorrectlyTests() throws IOException, JSONException {

        String event = "{\"type\":\"RoomOpened\",\"TimeStamp\":\"2024-05-13T02:45:27.727446667\", \"RoomNo\":\"101\"}";
        sut.eventToDatabase(event);

        verify(eventRepo, times(1)).save(any(EventBase.class));
    }
}
