package org.example.booking_project;

import com.fasterxml.jackson.databind.json.JsonMapper;
import org.example.booking_project.models.Shipper;
import org.example.booking_project.service.impl.ShipperServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;

import java.net.URL;


@ComponentScan
public class FetchShippers implements CommandLineRunner {

private final ShipperServiceImpl ssimpl;

    public FetchShippers(ShipperServiceImpl ssimpl) {
        this.ssimpl = ssimpl;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Jahapp! Nu kör vi FetchShippers!");

        JsonMapper jm = new JsonMapper();

        Shipper[] allShippers = jm.readValue(new URL("https://javaintegration.systementor.se/shippers"),
                Shipper[].class);

        for (Shipper s : allShippers){
            ssimpl.updateOrAddShipper(s.getId(), ssimpl.shipperToShipperDTO(s));
        }
    }


}
