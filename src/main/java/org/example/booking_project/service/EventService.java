package org.example.booking_project.service;

import org.example.booking_project.Dtos.RoomCleaningFinishedDTO;
import org.example.booking_project.Dtos.RoomCleaningStartedDTO;
import org.example.booking_project.Dtos.RoomClosedDTO;
import org.example.booking_project.Dtos.RoomOpenedDTO;

public interface EventService {
    void saveRoomCleaningFinishedEvent(RoomCleaningFinishedDTO eventDto);
    void saveRoomCleaningStartedEvent(RoomCleaningStartedDTO eventDto);
    void saveRoomClosedEvent(RoomClosedDTO eventDto);
    void saveRoomOpenedEvent(RoomOpenedDTO eventDto);

}
