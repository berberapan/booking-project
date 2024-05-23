package org.example.booking_project;

import org.example.booking_project.service.impl.EventServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component
@ComponentScan
public class ReadQueueApp implements CommandLineRunner {
    public EventServiceImpl eventService;

    @Override
    public void run(String... args) throws Exception {
        eventService.getEventFromQueue();
    }
}