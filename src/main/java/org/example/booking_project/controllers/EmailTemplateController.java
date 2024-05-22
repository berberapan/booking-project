package org.example.booking_project.controllers;

import org.example.booking_project.models.EmailTemplate;
import org.example.booking_project.service.impl.EmailTemplateServiceImpl;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("admin/templates")
public class EmailTemplateController {

    private final EmailTemplateServiceImpl emailTemplateServiceImpl;

    private JavaMailSender mailSender;

    public EmailTemplateController(EmailTemplateServiceImpl emailTemplateServiceImpl) {
        this.emailTemplateServiceImpl = emailTemplateServiceImpl;
    }

    @GetMapping
    public String listTemplates(Model model) {
        model.addAttribute("templates", emailTemplateServiceImpl.getAllTemplates());
        return "emailTemplates";
    }

    @GetMapping("/add")
    public String showAddTemplateForm(Model model) {
        model.addAttribute("emailTemplate", new EmailTemplate());
        return "addTemplate";
    }

    @GetMapping("/edit/{id}")
    public String showEditTemplateForm(@PathVariable Long id, Model model) {
        EmailTemplate emailTemplate = emailTemplateServiceImpl.getTemplateById(id);
        if (emailTemplate != null) {
            model.addAttribute("emailTemplate", emailTemplate);
            return "addTemplate";
        } else {
            return "redirect:/admin/templates";
        }
    }

    @PostMapping("/save")
    public String saveTemplate(@ModelAttribute EmailTemplate emailTemplate) {
        emailTemplateServiceImpl.saveTemplate(emailTemplate);
        return "redirect:/admin/templates";
    }

    @GetMapping("/delete/{id}")
    public String deleteTemplate(@PathVariable Long id) {
        emailTemplateServiceImpl.deleteTemplateById(id);
        return "redirect:/admin/templates";
    }
}
