package org.example.booking_project.repos;

import org.example.booking_project.models.EventBase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepo extends JpaRepository<EventBase, Long> {

}
