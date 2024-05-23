package org.example.booking_project.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.example.booking_project.models.EmailTemplate;
import org.example.booking_project.repos.EmailTemplateRepo;
import org.example.booking_project.service.EmailTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public EmailTemplateRepo emailTemplateRepo;

    @Autowired
    private final JavaMailSender mailSender;

    @Autowired
    private final TemplateEngine customTemplateEngine;


    public EmailTemplateServiceImpl(EmailTemplateRepo emailTemplateRepo, JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.emailTemplateRepo = emailTemplateRepo;
        this.customTemplateEngine = templateEngine;
    }

    public String processTemplate(EmailTemplate template, Map<String, Object> variables) {
        Context context = new Context();
        context.setVariables(variables);
        return customTemplateEngine.process(template.getBody(), context);
    }

    public void sendBookingConfirmationEmail(String email, String name, String phone, LocalDate checkInDate, LocalDate checkOutDate, String roomNumber, String bookingNumber, double totalPrice) throws MessagingException {
        EmailTemplate template = emailTemplateRepo.findByName("bookingEmailConfirmation");

        Map<String, Object> variables = new HashMap<>();
        variables.put("email", email);
        variables.put("name", name);
        variables.put("phone", phone);
        variables.put("checkInDate", checkInDate);
        variables.put("checkOutDate", checkOutDate);
        variables.put("roomNumber", roomNumber);
        variables.put("bookingNumber", bookingNumber);
        variables.put("totalPrice", String.format("%.2f", totalPrice));

        String body = processTemplate(template, variables);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(email);
        helper.setSubject(template.getSubject());
        helper.setText(body, true);

        mailSender.send(message);

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
    public List<EmailTemplate> getAllTemplates() {
        return emailTemplateRepo.findAll();
    }

    @Override
    public EmailTemplate getTemplateById(Long id) {
        Optional<EmailTemplate> templateOptional = emailTemplateRepo.findById(id);
        return templateOptional.orElse(null);
    }

    @Override
    public void deleteTemplateById(Long id) {
        emailTemplateRepo.deleteById(id);
    }
}
