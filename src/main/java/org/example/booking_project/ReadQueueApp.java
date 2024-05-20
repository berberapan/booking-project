package org.example.booking_project;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
public class ReadQueueApp implements CommandLineRunner {


    private String queueName = "9ac8f638-965a-44af-8204-d51b025d2dd1";
    @Override
    public void run(String... args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("128.140.81.47");
        factory.setUsername("djk47589hjkew789489hjf894");
        factory.setPassword("sfdjkl54278frhj7");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();


        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);


        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
            // https://www.baeldung.com/jackson-annotations#bd-jackson-polymorphic-type-handling-annotations
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });


    }

}