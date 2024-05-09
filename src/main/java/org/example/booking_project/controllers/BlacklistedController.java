package org.example.booking_project.controllers;

import org.example.booking_project.service.impl.BlacklistedServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BlacklistedController {


    private final BlacklistedServiceImpl blacklistedServiceImpl;


    public BlacklistedController(BlacklistedServiceImpl blacklistedServiceImpl) {
        this.blacklistedServiceImpl = blacklistedServiceImpl;
    }

    @PostMapping("https://javabl.systementor.se/api/gul/blacklist/{email}")
    public void addBlacklisted(@PathVariable String email){

    }
}
