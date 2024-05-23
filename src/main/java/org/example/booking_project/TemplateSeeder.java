package org.example.booking_project;

import org.example.booking_project.models.EmailTemplate;
import org.example.booking_project.repos.EmailTemplateRepo;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class TemplateSeeder {

    private final EmailTemplateRepo emailTemplateRepo;

    public TemplateSeeder(EmailTemplateRepo emailTemplateRepo) {
        this.emailTemplateRepo = emailTemplateRepo;
    }

    public void seedTemplate() throws IOException {
        // Läs in HTML-filen som en sträng
        String htmlContent = loadHtmlFromFile();

        // Skapa en EmailTemplate-instans och spara i databasen
        EmailTemplate template = new EmailTemplate("bookingEmailConfirmation", "Booking Confirmation", htmlContent);
        emailTemplateRepo.save(template);
    }

    private String loadHtmlFromFile() throws IOException {
        // Läs in HTML-filen som en sträng
        Path path = Paths.get("src/main/resources/templates/bookingEmailConfirmation.html");
        return Files.readString(path);
    }
}
