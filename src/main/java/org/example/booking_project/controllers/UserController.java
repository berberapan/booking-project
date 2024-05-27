package org.example.booking_project.controllers;

import org.example.booking_project.Dtos.UserDTO;
import org.example.booking_project.repos.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    UserRepo userRepo;

    @GetMapping("/login")
    public String login() {
        return "login.html";
    }

    @GetMapping("/user")
    @PreAuthorize("hasAuthority=('admin')")
    public String showSearchPage(){
        return "userAccounts.html";
    }

    @PostMapping("/user/search")
    @PreAuthorize("hasAuthority=('admin')")
    public String searchUser(@RequestParam String search, Model model){
        model.addAttribute("user", new ModelMapper().map(userRepo.getUserByUsername(search), UserDTO.class) );
        return "userAccounts.html";
    }
}
