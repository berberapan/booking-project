package org.example.booking_project.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.example.booking_project.models.EmailTemplate;
import org.example.booking_project.repos.EmailTemplateRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class EmailTemplateServiceImplTests {

    @Mock
    private EmailTemplateRepo emailTemplateRepo;

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private TemplateEngine templateEngine;

    @InjectMocks
    private EmailTemplateServiceImpl emailTemplateService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcessTemplate() {
        EmailTemplate template = new EmailTemplate();
        template.setBody("Hello, ${name}");

        Map<String, Object> variables = new HashMap<>();
        variables.put("name", "John");

        when(templateEngine.process(any(String.class), any(Context.class))).thenReturn("Hello, John");

        String result = emailTemplateService.processTemplate(template, variables);

        assertEquals("Hello, John", result);
        verify(templateEngine).process(any(String.class), any(Context.class));
    }

    @Test
    public void testSendBookingConfirmationEmail() throws MessagingException {
        EmailTemplate template = new EmailTemplate();
        template.setName("bookingEmailConfirmation");
        template.setSubject("Booking Confirmation");
        template.setBody("Email body template");

        when(emailTemplateRepo.findByName("bookingEmailConfirmation")).thenReturn(template);
        when(templateEngine.process(any(String.class), any(Context.class))).thenReturn("Processed email body");

        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
        doNothing().when(mailSender).send(any(MimeMessage.class));

        emailTemplateService.sendBookingConfirmationEmail("test@example.com", "John", "123456789", LocalDate.now(), LocalDate.now().plusDays(1), "101", "123456", 200.00);

        verify(mailSender).send(mimeMessage);
    }

    @Test
    public void testGetEmailTemplateByName() {
        EmailTemplate template = new EmailTemplate();
        template.setName("templateName");

        when(emailTemplateRepo.findByName("templateName")).thenReturn(template);

        EmailTemplate result = emailTemplateService.getEmailTemplateByName("templateName");

        assertEquals(template, result);
        verify(emailTemplateRepo).findByName("templateName");
    }

    @Test
    public void testSaveTemplate() {
        EmailTemplate template = new EmailTemplate();
        emailTemplateService.saveTemplate(template);
        verify(emailTemplateRepo).save(template);
    }

    @Test
    public void testGetAllTemplates() {
        emailTemplateService.getAllTemplates();
        verify(emailTemplateRepo).findAll();
    }

    @Test
    public void testGetTemplateById() {
        EmailTemplate template = new EmailTemplate();
        when(emailTemplateRepo.findById(1L)).thenReturn(Optional.of(template));

        EmailTemplate result = emailTemplateService.getTemplateById(1L);

        assertEquals(template, result);
        verify(emailTemplateRepo).findById(1L);
    }

    @Test
    public void testDeleteTemplateById() {
        emailTemplateService.deleteTemplateById(1L);
        verify(emailTemplateRepo).deleteById(1L);
    }

    @Test
    public void testRenderHtmlContent() {
        EmailTemplate template = new EmailTemplate();
        template.setBody("HTML content");

        String result = emailTemplateService.renderHtmlContent(template);

        assertEquals("HTML content", result);
    }
}
