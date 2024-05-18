package org.example.booking_project;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.example.booking_project.controllers.BookingController;
import org.example.booking_project.models.EventBase;
import org.example.booking_project.models.Shipper;
import org.example.booking_project.repos.EventRepo;
import org.example.booking_project.service.impl.ShipperServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;

import java.net.URL;


@ComponentScan
public class FetchShippers implements CommandLineRunner {

    private final ShipperServiceImpl ssimpl;
    private final EventRepo eventRepo;

    public FetchShippers(ShipperServiceImpl ssimpl, EventRepo eventRepo) {
        this.ssimpl = ssimpl;
        this.eventRepo = eventRepo;
    }

    @Override
    public void run(String... args) throws Exception {

        JsonMapper jm = new JsonMapper();

        Shipper[] allShippers = jm.readValue(new URL("https://javaintegration.systementor.se/shippers"),
                Shipper[].class);

        for (Shipper s : allShippers) {
            ssimpl.updateOrAddShipper(s.getId(), ssimpl.shipperToShipperDTO(s));
        }

/*
//------OBS TA BORT SEN!!!! = Mock-data för Room event
        ObjectMapper objMapper = new ObjectMapper();
        objMapper.findAndRegisterModules();
        String eventJson = "[{\"type\":\"RoomClosed\",\"TimeStamp\":\"2024-05-13T02:45:27.727446667\",\"RoomNo\":\"104\"},{\"type\":\"RoomCleaningFinished\",\"TimeStamp\":\"2024-05-13T02:45:27.727446667\",\"RoomNo\":\"104\",\"CleaningByUser\":\"Britt-Marie Henrisch\"}]";
        EventBase[] allEvents = objMapper.readValue(eventJson,
                EventBase[].class);

        for (EventBase ev : allEvents) {
            eventRepo.save(ev);
            //ssimpl.updateOrAddShipper(s.getId(), ssimpl.shipperToShipperDTO(s));
        }
        */
    }
}
