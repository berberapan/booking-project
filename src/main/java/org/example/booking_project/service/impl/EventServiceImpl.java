package org.example.booking_project.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.example.booking_project.models.EventBase;
import org.example.booking_project.repos.EventRepo;
import org.example.booking_project.service.EventService;

public class EventServiceImpl implements EventService {
    private final EventRepo eventRepo;

    public EventServiceImpl(EventRepo eventRepo) {
        this.eventRepo = eventRepo;
    }


    public void getEventFromQueue() {

        try {
            String queueName = "9ac8f638-965a-44af-8204-d51b025d2dd1";
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("128.140.81.47");
            factory.setUsername("djk47589hjkew789489hjf894");
            factory.setPassword("sfdjkl54278frhj7");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
                eventToDatabase(message);
                System.out.println("Inserted " + message);
            };
            channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
            });
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void eventToDatabase(String message) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        EventBase event = mapper.readValue(message, EventBase.class);
        eventRepo.save(event);
    }
}
