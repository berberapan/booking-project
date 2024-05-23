package org.example.booking_project;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.example.booking_project.repos.EventRepo;
import org.example.booking_project.service.impl.EventServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component
@ComponentScan
public class ReadQueueApp implements CommandLineRunner {

    private final EventRepo eventRepo;
    private final EventServiceImpl eventService;

    public ReadQueueApp(EventRepo eventRepo, EventServiceImpl eventService) {
        this.eventRepo = eventRepo;
        this.eventService = eventService;
    }

    @Override
    public void run(String... args) {

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