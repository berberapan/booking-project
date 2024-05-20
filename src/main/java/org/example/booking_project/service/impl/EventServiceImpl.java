package org.example.booking_project.service.impl;

import org.example.booking_project.Dtos.*;
import org.example.booking_project.models.*;
import org.example.booking_project.repos.EventRepo;
import org.example.booking_project.service.EventService;
import org.springframework.stereotype.Service;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepo eventRepo;

    public EventServiceImpl(EventRepo eventRepo) {
        this.eventRepo = eventRepo;
    }

    @Override
    public void saveRoomCleaningFinishedEvent(RoomCleaningFinishedDTO eventDto) {
        RoomCleaningFinished event = roomCleaningFinishedDTOtoroomCleaningFinished(eventDto);
        eventRepo.save(event);
    }

    @Override
    public void saveRoomCleaningStartedEvent(RoomCleaningStartedDTO eventDto) {
        RoomCleaningStarted event = roomCleaningStartedDTOtoRoomCleaningStarted(eventDto);
        eventRepo.save(event);
    }

    @Override
    public void saveRoomClosedEvent(RoomClosedDTO eventDto) {
        RoomClosed event = roomClosedDTOtoRoomClosed(eventDto);
        eventRepo.save(event);
    }

    @Override
    public void saveRoomOpenedEvent(RoomOpenedDTO eventDto) {
        RoomOpened event = roomOpenedDTOtoRoomOpened(eventDto);
        eventRepo.save(event);
    }

    private RoomCleaningFinished roomCleaningFinishedDTOtoroomCleaningFinished(RoomCleaningFinishedDTO dto) {
        RoomCleaningFinished event = new RoomCleaningFinished();

        return event;
    }

    private RoomCleaningStarted roomCleaningStartedDTOtoRoomCleaningStarted(RoomCleaningStartedDTO dto) {
        RoomCleaningStarted event = new RoomCleaningStarted();

        return event;
    }

    private RoomClosed roomClosedDTOtoRoomClosed(RoomClosedDTO dto) {
        RoomClosed event = new RoomClosed();

        return event;
    }

    private RoomOpened roomOpenedDTOtoRoomOpened(RoomOpenedDTO dto) {
        RoomOpened event = new RoomOpened();

        return event;
    }
}
