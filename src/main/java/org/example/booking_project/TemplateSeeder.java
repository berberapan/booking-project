package org.example.booking_project;

import org.example.booking_project.models.EmailTemplate;
import org.example.booking_project.repos.EmailTemplateRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class TemplateSeeder {

    private static final Logger logger = LoggerFactory.getLogger(TemplateSeeder.class);

    private final EmailTemplateRepo emailTemplateRepo;

    public TemplateSeeder(EmailTemplateRepo emailTemplateRepo) {
        this.emailTemplateRepo = emailTemplateRepo;
    }

    public void seedTemplates() throws IOException {

        List<EmailTemplate> templates = List.of(
                new EmailTemplate("bookingEmailConfirmation", "Bokningsbekräftelse", loadHtmlFromFile("bookingEmailConfirmation.html")),
                new EmailTemplate("cancellationConfirmation", "Avbokningsbekräftelse", loadHtmlFromFile("cancellationConfirmation.html")),
                new EmailTemplate("thankYou", "Tack för din vistelse", loadHtmlFromFile("thankYou.html")),
                new EmailTemplate("checkInReminder", "Påminnelse om incheckning", loadHtmlFromFile("checkInReminder.html")),
                new EmailTemplate("welcomeMessage", "Välkomstmeddelande", loadHtmlFromFile("welcomeMessage.html")));

        for (EmailTemplate template : templates) {
            emailTemplateRepo.save(template);
            logger.info("Templates seeded successfully.");
        }
    }
    private String loadHtmlFromFile(String filename) throws IOException {
        Path path = Paths.get("src/main/resources/templates/emailTemplates/" + filename);
        return Files.readString(path);
    }
}
