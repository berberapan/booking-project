package org.example.booking_project.service;


import jakarta.mail.MessagingException;
import org.example.booking_project.models.EmailTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface EmailTemplateService {

    public String processTemplate(EmailTemplate template, Map<String, Object> variables);

    public void sendBookingConfirmationEmail(String email, String name, String phone, LocalDate checkInDate, LocalDate checkOutDate, String roomNumber, String bookingNumber, double totalPrice) throws MessagingException;

    public EmailTemplate getEmailTemplateByName(String name);

    public List<EmailTemplate> getAllTemplates();

    public EmailTemplate getTemplateById(Long id);

    public void deleteTemplateById(Long id);

    public void saveTemplate(EmailTemplate emailTemplate);

    public String renderHtmlContent(EmailTemplate template);

}
