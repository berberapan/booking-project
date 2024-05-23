package org.example.booking_project.controllers;

import org.example.booking_project.models.EmailTemplate;
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

    public EmailTemplateController(EmailTemplateServiceImpl emailTemplateServiceImpl, EmailTemplateRepo emailTemplateRepo) {
        this.emailTemplateServiceImpl = emailTemplateServiceImpl;
        this.emailTemplateRepo = emailTemplateRepo;
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
            return "editTemplate";
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


