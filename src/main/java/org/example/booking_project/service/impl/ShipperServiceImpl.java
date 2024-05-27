package org.example.booking_project.service.impl;

import com.fasterxml.jackson.databind.json.JsonMapper;
import org.example.booking_project.Dtos.ShipperDTO;
import org.example.booking_project.configs.IntegrationsProperties;
import org.example.booking_project.controllers.BookingController;
import org.example.booking_project.models.Shipper;
import org.example.booking_project.repos.ShipperRepo;
import org.example.booking_project.service.ShipperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
public class ShipperServiceImpl implements ShipperService {

    private final ShipperRepo shipperRepo;
    private final JsonStreamProvider jsp;
    private static final Logger log = LoggerFactory.getLogger(ShipperServiceImpl.class);
    IntegrationsProperties properties;

    public ShipperServiceImpl(ShipperRepo shipperRepo, JsonStreamProvider jsp, IntegrationsProperties properties){
        this.shipperRepo = shipperRepo;
        this.jsp = jsp;
        this.properties = properties;
    }

    @Override
    public ShipperDTO shipperToShipperDTO(Shipper s) {
        return ShipperDTO.builder().id(s.getId()).email(s.getEmail()).companyName(s.getCompanyName())
                .contactName(s.getContactName()).contactTitle(s.getContactTitle()).streetAddress(s.getStreetAddress())
                        .city(s.getCity()).postalCode(s.getPostalCode()).country(s.getCountry()).phone(s.getPhone())
                        .fax(s.getFax()).build();
    }

    @Override
    public Shipper shipperDTOToShipper(ShipperDTO s) {
        return Shipper.builder().id(s.getId()).email(s.getEmail()).companyName(s.getCompanyName())
                .contactName(s.getContactName()).contactTitle(s.getContactTitle()).streetAddress(s.getStreetAddress())
                .city(s.getCity()).postalCode(s.getPostalCode()).country(s.getCountry()).phone(s.getPhone())
                .fax(s.getFax()).build();
    }

    @Override
    public void updateOrAddShipper(Long id, ShipperDTO shipperDTO) {
        Shipper existingShipper = shipperRepo.findById(id).orElse(null);
        if (existingShipper != null) {
            existingShipper.setEmail(shipperDTO.getEmail());
            existingShipper.setCompanyName(shipperDTO.getCompanyName());
            existingShipper.setContactName(shipperDTO.getContactName());
            existingShipper.setContactTitle(shipperDTO.getContactTitle());
            existingShipper.setStreetAddress(shipperDTO.getStreetAddress());
            existingShipper.setCity(shipperDTO.getCity());
            existingShipper.setPostalCode(shipperDTO.getPostalCode());
            existingShipper.setCountry(shipperDTO.getCountry());
            existingShipper.setPhone(shipperDTO.getPhone());
            existingShipper.setFax(shipperDTO.getFax());
            shipperRepo.save(existingShipper);
            log.info("{} updated", existingShipper.getCompanyName());
        } else {
            Shipper newShipper = shipperDTOToShipper(shipperDTO);
            shipperRepo.save(newShipper);
            log.info("{} added", newShipper.getCompanyName());
        }
    }

    @Override
    public Shipper[] shippersJsonMapper() throws IOException {
        JsonMapper jm = new JsonMapper();
        InputStream data = jsp.getDataStream(properties.shippers.fetchurl);
        return jm.readValue(data, Shipper[].class);
    }

    @Override
    public void shippersToDatabase(Shipper[] allShippers) {
        for (Shipper s : allShippers) {
            updateOrAddShipper(s.getId(), shipperToShipperDTO(s));
        }
    }
}
