package org.example.booking_project;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.example.booking_project.configs.IntegrationsProperties;
import org.example.booking_project.repos.EventRepo;
import org.example.booking_project.service.impl.EventServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component
@ComponentScan
public class ReadQueueApp implements CommandLineRunner {

    private final EventRepo eventRepo;
    private final EventServiceImpl eventService;

    @Autowired
    IntegrationsProperties properties;

    public ReadQueueApp(EventRepo eventRepo, EventServiceImpl eventService) {
        this.eventRepo = eventRepo;
        this.eventService = eventService;
    }

    @Override
    public void run(String... args) {

        try {
            String queueName = properties.events.queueName;
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(properties.events.factoryHost);
            factory.setUsername(properties.events.factoryUsername);
            factory.setPassword(properties.events.factoryPassword);
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
                eventService.eventToDatabase(message);
                System.out.println("Inserted " + message);
            };
            channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
            });
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}