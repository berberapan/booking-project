package org.example.booking_project.service;

import org.example.booking_project.Dtos.ShipperDTO;
import org.example.booking_project.models.Shipper;

import java.io.IOException;

public interface ShipperService {
    public ShipperDTO shipperToShipperDTO(Shipper s);

    public Shipper shipperDTOToShipper(ShipperDTO s);

    public void updateOrAddShipper(Long id, ShipperDTO shipperDTO);

    public Shipper[] shippersJsonMapper() throws IOException;

    public void shippersToDatabase(Shipper[] shippers);
}
