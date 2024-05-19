package org.example.booking_project;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.example.booking_project.controllers.BookingController;
import org.example.booking_project.models.EventBase;
import org.example.booking_project.models.Shipper;
import org.example.booking_project.repos.EventRepo;
import org.example.booking_project.service.impl.JsonStreamProvider;
import org.example.booking_project.service.impl.ShipperServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;

import java.io.InputStream;
import java.net.URL;


@ComponentScan
public class FetchShippers implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(FetchShippers.class);
    private final ShipperServiceImpl ssimpl;
    private final EventRepo eventRepo;
    private final JsonStreamProvider jsonStreamProvider;

    public FetchShippers(ShipperServiceImpl ssimpl, EventRepo eventRepo, JsonStreamProvider jsonStreamProvider) {
        this.ssimpl = ssimpl;
        this.eventRepo = eventRepo;
        this.jsonStreamProvider = jsonStreamProvider;
    }



    @Override
    public void run(String... args) throws Exception {
        log.info("Fetching shippers");
        ssimpl.shippersToDatabase(ssimpl.shippersJsonMapper());


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
