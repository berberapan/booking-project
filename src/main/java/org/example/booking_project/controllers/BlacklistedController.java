package org.example.booking_project.controllers;

import org.example.booking_project.Dtos.BlacklistedDTO;
import org.example.booking_project.Dtos.CustomerDTO;
import org.example.booking_project.service.impl.BlacklistedServiceImpl;
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
    public String searchBlacklisted(@RequestParam String email, Model model) throws IOException {
        BlacklistedDTO blacklistedDTO = new BlacklistedDTO();
        if (blacklistedServiceImpl.existsByEmail(email)) {
            blacklistedDTO = blacklistedServiceImpl.getBlacklistedByEmail(email);
            model.addAttribute("blacklistedNotFound", false);
            model.addAttribute("blacklistedFound", true);
            if(blacklistedDTO.ok){model.addAttribute("isOk", true);}
            else {model.addAttribute("isNotOk", true);}
        }
        else{
            blacklistedDTO.setEmail(email);
            model.addAttribute("blacklistedNotFound", true);
            model.addAttribute("blacklistedFound", false);
            model.addAttribute("isOk", true);
        }
        model.addAttribute("blacklisted", blacklistedDTO);
        model.addAttribute("blacklistedFormToggle", true);
        return "blacklistAdmin";
    }
    @PostMapping("/blacklist/update")
    public String updateBlacklist(@ModelAttribute BlacklistedDTO blacklistedDTO, Model model) {
        blacklistedServiceImpl.updateBlacklisted(blacklistedDTO);
        model.addAttribute("updated", true);
        return "blacklistAdmin";
    }
}
