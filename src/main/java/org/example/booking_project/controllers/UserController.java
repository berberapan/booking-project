package org.example.booking_project.controllers;

import lombok.AllArgsConstructor;
import org.example.booking_project.Dtos.UserDTO;
import org.example.booking_project.repos.UserRepo;
import org.example.booking_project.service.impl.UserDetailsServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

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
    public String updateUser(@RequestParam UUID Id@RequestParam(defaultValue = "false") boolean admin, @RequestParam(defaultValue = "false") boolean receptionist, @RequestParam String username, Model model) {
        UserDTO userDTO = UserDTO.builder().username(username).admin(admin).receptionist(receptionist).build();
        userService.updateUser(userDTO);
        model.addAttribute("user", userDTO);
        model.addAttribute("message", "Ny användare skapad");
        return "createUser.html";
    }
    @GetMapping("/user/create")
    public String redirectToCreateUser(Model model){
        model.addAttribute("admin", false);
        model.addAttribute("receptionist", false);
        return "createUser.html";
    }

    @PostMapping("/user/create")
    @PreAuthorize("hasAuthority('admin')")
    public String createUser(@RequestParam(defaultValue = "false") boolean admin, @RequestParam(defaultValue = "false") boolean receptionist,@RequestParam String username,@RequestParam String password, Model model) {
        UserDTO userDTO = UserDTO.builder().username(username).password(password).admin(admin).receptionist(receptionist).build();
        userRepo.save(userService.userDTOToUser(userDTO));
        model.addAttribute("message", "Ny användare skapad");
        model.addAttribute("user", userDTO);
        return "createUser.html";
    }

}
