package org.example.booking_project.service.impl;

import org.example.booking_project.Dtos.RoomDTO;
import org.example.booking_project.Dtos.ShipperDTO;
import org.example.booking_project.models.Shipper;
import org.example.booking_project.service.ShipperService;
import org.springframework.stereotype.Service;

@Service
public class ShipperServiceImpl implements ShipperService {
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
}
