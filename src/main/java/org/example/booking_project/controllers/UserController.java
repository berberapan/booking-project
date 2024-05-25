package org.example.booking_project.controllers;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.example.booking_project.Utils;
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
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

@Controller
public class UserController {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;


    @GetMapping("/login")
    public String login() {
        return "login.html";
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
        helper.setFrom("raven15@ethereal.email", "Raven");
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
