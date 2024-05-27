package org.example.booking_project.controllers;

import lombok.AllArgsConstructor;
import org.example.booking_project.Dtos.CustomerDTO;
import org.example.booking_project.Dtos.UserDTO;
import org.example.booking_project.repos.UserRepo;
import org.example.booking_project.service.impl.UserDetailsServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class UserController {
    UserRepo userRepo;
    UserDetailsServiceImpl userService;

    @GetMapping("/login")
    public String login() {
        return "login.html";
    }

    @GetMapping("/user")
    @PreAuthorize("hasAuthority('admin')")
    public String showSearchPage() {
        return "userAccount.html";
    }

    @PostMapping("/user/search")
    @PreAuthorize("hasAuthority('admin')")
    public String searchUser(@RequestParam String search, Model model) {

        UserDTO user = userService.userToUserDTO(userRepo.getUserByUsername(search));
        model.addAttribute("user", user);
        return "userAccount.html";
    }

    @PostMapping("/user/update")
    @PreAuthorize("hasAuthority('admin')")
    public String updateUser(@ModelAttribute CustomerDTO customerDTO, Model model) {

        return "";
    }
    @GetMapping("/user/create")
    public String redirectToCreateUser(){
        return "createUser.html";
    }

    @PostMapping("/user/create")
    @PreAuthorize("hasAuthority('admin')")
    public String createUser(@ModelAttribute UserDTO userDTO, Model model) {
        System.out.println(userService.userDTOToUser(userDTO));
        userRepo.save(userService.userDTOToUser(userDTO));
        model.addAttribute("message", "Ny användare skapad");
        model.addAttribute("user", userDTO);
        return "createUser.html";
    }

}
