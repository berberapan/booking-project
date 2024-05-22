package org.example.booking_project.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.example.booking_project.models.EmailTemplate;
import org.example.booking_project.repos.EmailTemplateRepo;
import org.example.booking_project.service.EmailTemplateService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class EmailTemplateServiceImpl implements EmailTemplateService {

    public EmailTemplateRepo emailTemplateRepo;
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;


    public EmailTemplateServiceImpl(EmailTemplateRepo emailTemplateRepo, JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.emailTemplateRepo = emailTemplateRepo;
        this.templateEngine = templateEngine;
    }

    @Override
    public EmailTemplate getEmailTemplateByName(String name) {
        return emailTemplateRepo.findByName(name);
    }

    @Override
    public void saveTemplate(EmailTemplate emailTemplate) {
        emailTemplateRepo.save(emailTemplate);
    }

    @Override
    public String parseTemplate(EmailTemplate template, Map<String, String> variables) {
        String body = template.getBody();
        for (Map.Entry<String, String> entry : variables.entrySet()) {
            String key = "{{" + entry.getKey() + "}}";
            String value = entry.getValue() != null ? entry.getValue() : "";
            body = body.replace(key, value);
        }
        return body;
    }

    public List<EmailTemplate> getAllTemplates() {
        return emailTemplateRepo.findAll();
    }

    @Override
    public EmailTemplate getTemplateById(Long id) {
        Optional<EmailTemplate> templateOptional = emailTemplateRepo.findById(id);
        return templateOptional.orElse(null);
    }

    public void deleteTemplateById(Long id) {
        emailTemplateRepo.deleteById(id);
    }

    public void sendBookingConfirmationEmail(String to, String name, LocalDate startDate, LocalDate endDate) throws MessagingException {
        EmailTemplate template = getEmailTemplateByName("Email");
        Map<String, String> variables = new HashMap<>();
        variables.put("name", name);
        variables.put("startDate", startDate.toString());
        variables.put("endDate", endDate.toString());

        String body = parseTemplate(template, variables);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(template.getSubject());
        helper.setText(body, true);

        mailSender.send(message);
    }

}
