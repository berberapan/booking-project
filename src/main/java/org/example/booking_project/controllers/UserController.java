package org.example.booking_project.controllers;

import lombok.AllArgsConstructor;
import org.example.booking_project.Dtos.UserDTO;
import org.example.booking_project.repos.UserRepo;
import org.example.booking_project.service.impl.UserDetailsServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.example.booking_project.Utils;
import org.example.booking_project.configs.IntegrationsProperties;
import org.example.booking_project.models.User;
import org.example.booking_project.service.impl.UserDetailsServiceImpl;
import org.modelmapper.internal.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.UUID;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;


@Controller
@AllArgsConstructor
public class UserController {
    UserRepo userRepo;
    UserDetailsServiceImpl userService;

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;
    @Autowired
    private IntegrationsProperties properties;


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
    public String updateUser(@RequestParam UUID id, @RequestParam(defaultValue = "false") boolean admin, @RequestParam(defaultValue = "false") boolean receptionist, @RequestParam String username, Model model) {
        UserDTO userDTO = UserDTO.builder().id(id).username(username).admin(admin).receptionist(receptionist).build();
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


    @GetMapping("/forgotPassword")
    public String forgotPassword() {
        return "forgotPassword.html";
    }

    @PostMapping("/forgotPassword")
    public String handleForgotPassword(Model model, HttpServletRequest request) {
        String email = request.getParameter("email");
        String resetPasswordToken = RandomString.make(30);

        try {
            userDetailsServiceImpl.setNewResetPasswordToken(resetPasswordToken, email);
            String resetPasswordLink = Utils.getSiteURL(request) + "/resetPassword?token=" + resetPasswordToken;
            sendEmail(email, resetPasswordLink);
            model.addAttribute("message", "Email har skickats till din adress.");
        } catch (UsernameNotFoundException e) {
            model.addAttribute("error", "Ingen användare kunde hittas med den emailadressen.");
        } catch (Exception e) {
            model.addAttribute("error", "Emailet kunde inte skickas.");
        }
        return "forgotPassword.html";
    }

    public void sendEmail(String email, String link) throws MessagingException, UnsupportedEncodingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setFrom(properties.emails.emailAddress, properties.emails.personal);
        helper.setTo(email);

        String subject = "Länk för återställning av lösenord.";
        String content = "<p>Här är länken för att återställa lösenordet.</p>" +
                "<p><a href=\"" + link + "\">Byt lösenord</a></p>";

        helper.setSubject(subject);
        helper.setText(content, true);

        mailSender.send(mimeMessage);
    }

    @GetMapping("/resetPassword")
    public String resetPassword(@RequestParam(value = "token") String token, Model model) {
        User user = userDetailsServiceImpl.getUserByToken(token);
        model.addAttribute("token", token);
        if (user == null | user.getResetPasswordExpiration().isBefore(LocalDateTime.now())) {
            model.addAttribute("error", "Ogiltig token.");
            return "resetPassword.html";
        }
        return "resetPassword.html";
    }

    @PostMapping("/resetPassword")
    public String handleResetPassword(Model model, HttpServletRequest request) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        User user = userDetailsServiceImpl.getUserByToken(token);

        if (!password.equals(confirmPassword)) {
            model.addAttribute("mismatch", "Båda fälten måste matcha varandra.");
            return "resetPassword.html";
        }
        if (user == null | user.getResetPasswordExpiration().isBefore(LocalDateTime.now())) {
            model.addAttribute("error", "Ogiltig token.");
            return "resetPassword.html";
        } else {
            userDetailsServiceImpl.updatePassword(user, password);
        }
        return "login.html";
    }

}
