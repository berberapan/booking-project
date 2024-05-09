package org.example.booking_project.controllers;

import org.example.booking_project.service.impl.ShipperServiceImpl;
import org.springframework.stereotype.Controller;

@Controller
public class ShipperController {
    private final ShipperServiceImpl ssimpl;

    public ShipperController(ShipperServiceImpl ssimpl) {
        this.ssimpl = ssimpl;
    }

}
