package org.example.booking_project.repos;

import org.example.booking_project.models.EmailTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailTemplateRepo extends JpaRepository<EmailTemplate, Long> {

    EmailTemplate findByName(String name);
}
