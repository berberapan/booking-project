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

    }
}
