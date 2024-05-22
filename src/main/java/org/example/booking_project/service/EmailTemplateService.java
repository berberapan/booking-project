package org.example.booking_project.service;


import jakarta.mail.MessagingException;
import org.example.booking_project.models.EmailTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface EmailTemplateService {


    public EmailTemplate getEmailTemplateByName(String name);

    public List<EmailTemplate> getAllTemplates();

    public EmailTemplate getTemplateById(Long id);

    public void deleteTemplateById(Long id);

    public void saveTemplate(EmailTemplate emailTemplate);

    public String parseTemplate(EmailTemplate template, Map<String, String> variables);

    public void sendBookingConfirmationEmail(String to, String name, LocalDate startDate, LocalDate endDate) throws MessagingException;
}
