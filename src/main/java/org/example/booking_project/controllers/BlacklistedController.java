package org.example.booking_project.controllers;

import org.example.booking_project.Dtos.BlacklistedDTO;
import org.example.booking_project.Dtos.CustomerDTO;
import org.example.booking_project.service.impl.BlacklistedServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
public class BlacklistedController {


    private final BlacklistedServiceImpl blacklistedServiceImpl;


    public BlacklistedController(BlacklistedServiceImpl blacklistedServiceImpl) {
        this.blacklistedServiceImpl = blacklistedServiceImpl;
    }

    @GetMapping("/blacklist")
    @PreAuthorize("hasAuthority('admin')")
    public String showBlacklistAdminPage(Model model) {
        model.addAttribute("blacklisted", new BlacklistedDTO());
        model.addAttribute("blacklistedFormToggle", false);
        model.addAttribute("blacklistedNotFound", false);
        model.addAttribute("updated", false);
        model.addAttribute("blacklistedExists", false);
        model.addAttribute("created", false);
        return "blacklistAdmin";
    }

    @PostMapping("/blacklist")
    @PreAuthorize("hasAuthority('admin')")
    public String searchBlacklisted(@RequestParam String email, Model model) throws IOException {
        BlacklistedDTO blacklisted = new BlacklistedDTO();
        if (blacklistedServiceImpl.existsByEmail(email)) {
            blacklisted = blacklistedServiceImpl.getBlacklistedByEmail(email);
            model.addAttribute("blacklistedNotFound", false);
            model.addAttribute("blacklistedFound", true);
            if(blacklisted.ok){model.addAttribute("isOk", true);}
            else {model.addAttribute("isNotOk", true);}
        }
        else{
            blacklisted.setEmail(email);
            blacklisted.setOk(true);
            model.addAttribute("blacklistedNotFound", true);
            model.addAttribute("blacklistedFound", false);
            model.addAttribute("isOk", true);
        }
        model.addAttribute("blacklisted", blacklisted);
        model.addAttribute("blacklistedFormToggle", true);
        return "blacklistAdmin";
    }
    @PostMapping("/blacklist/update")
    @PreAuthorize("hasAuthority('admin')")
    public String updateBlacklist(@ModelAttribute BlacklistedDTO blacklistedDTO, Model model) throws IOException {
        blacklistedServiceImpl.updateBlacklisted(blacklistedDTO);
        model.addAttribute("updated", true);
        return "blacklistAdmin";
    }
}
