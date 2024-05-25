package org.example.booking_project.controllers;

import org.example.booking_project.models.EmailTemplate;
import org.example.booking_project.repos.CustomerRepo;
import org.example.booking_project.repos.EmailTemplateRepo;
import org.example.booking_project.service.impl.EmailTemplateServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;


@Controller
@RequestMapping("admin/templates")
public class EmailTemplateController {

    private final EmailTemplateServiceImpl emailTemplateServiceImpl;
    private final EmailTemplateRepo emailTemplateRepo;
    private final CustomerRepo customerRepo;

    public EmailTemplateController(EmailTemplateServiceImpl emailTemplateServiceImpl, EmailTemplateRepo emailTemplateRepo, CustomerRepo customerRepo) {
        this.emailTemplateServiceImpl = emailTemplateServiceImpl;
        this.emailTemplateRepo = emailTemplateRepo;
        this.customerRepo = customerRepo;
    }

    @GetMapping
    public String listTemplates(Model model) {
        model.addAttribute("templates", emailTemplateServiceImpl.getAllTemplates());
        return "emailTemplates";
    }

    @GetMapping("/add")
    public String showAddTemplateForm(Model model) {
        EmailTemplate emailTemplate = new EmailTemplate();
        emailTemplate.setBody("""
                <!DOCTYPE html>
                <html xmlns:th="http://www.thymeleaf.org">
                <head>
                    <title>Bokningsbekräftelse</title>
                    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
                    <style>
                         .highlight {
                                    font-weight: bold;
                                    color: #007bff;
                         }
                    </style>
                </head>
                <body>
                <div class="container mt-4">
                    <div class="card">
                        <div class="card-body">
                            <h1 class="card-title">Hej och välkommen till Glada Utterns pensionat</h1>
                            <p>Hej <span th:text="${name}" class="highlight">namn</span>,</p>
                            <p>Tack för din bokning! Här är dina bokningsdetaljer:</p>
                            <p>Email: <span th:text="${email}" class="highlight">email</span></p>
                            <p>Telefon: <span th:text="${phone}" class="highlight">telefonnummer</span></p>
                            <p>Incheckningsdatum: <span th:text="${checkInDate}" class="highlight">incheckningsdatum</span></p>
                            <p>Utcheckningsdatum: <span th:text="${checkOutDate}" class="highlight">utcheckningsdatum</span></p>
                            <p>Rumsnummer: <span th:text="${roomNumber}" class="highlight">rumsnummer</span></p>
                            <p>Bokningsnummer: <span th:text="${bookingNumber}" class="highlight">bokningsnummer</span></p>
                            <p>Totalpris: <span th:text="${totalPrice}" class="highlight">totala priset</span></p>
                        </div>
                    </div>
                </div>
                </body>
                </html>
                """);

        model.addAttribute("emailTemplate", emailTemplate);
        return "addTemplate";
    }

    @GetMapping("/edit/{id}")
    public String showEditTemplateForm(@PathVariable Long id, Model model) {
        EmailTemplate emailTemplate = emailTemplateServiceImpl.getTemplateById(id);
        if (emailTemplate != null) {
            model.addAttribute("emailTemplate", emailTemplate);
            return "editTemplate";
        } else {
            return "redirect:/admin/templates";
        }
    }

    @GetMapping("/editHtml/{id}")
    public String showEditHtmlForm(@PathVariable Long id, Model model) {
        EmailTemplate emailTemplate = emailTemplateServiceImpl.getTemplateById(id);
        if (emailTemplate != null) {
            model.addAttribute("emailTemplate", emailTemplate);
            return "editHtml";
        } else {
            return "redirect:/admin/templates";
        }
    }

    @PostMapping("/update/{id}")
    public String updateTemplate(@PathVariable Long id, @ModelAttribute EmailTemplate updatedTemplate) {
        Optional<EmailTemplate> templateOptional = emailTemplateRepo.findById(id);

        if (templateOptional.isPresent()) {
            EmailTemplate existingTemplate = templateOptional.get();
            existingTemplate.setName(updatedTemplate.getName());
            existingTemplate.setSubject(updatedTemplate.getSubject());
            existingTemplate.setBody(updatedTemplate.getBody());
            emailTemplateRepo.save(existingTemplate);
        }
        return "redirect:/admin/templates";
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

    @GetMapping("/preview/{id}")
    public String previewTemplate(@PathVariable Long id, Model model) {
        Optional<EmailTemplate> templateOptional = emailTemplateRepo.findById(id);

        if (templateOptional.isPresent()) {
            EmailTemplate template = templateOptional.get();
            String htmlContent = emailTemplateServiceImpl.renderHtmlContent(template);
            model.addAttribute("htmlContent", htmlContent);
            return "templatePreview";
        } else {
            return "error";
        }
    }
}


