package org.example.booking_project;

import org.example.booking_project.configs.MailConfig;
import org.example.booking_project.controllers.BookingController;
import org.example.booking_project.controllers.CustomerController;
import org.example.booking_project.controllers.RoomController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {booking_project.class, MailConfig.class})
class BookingProjectApplicationTests {

    @Value(value="${local.server.port}")
    private int port;

    @Autowired
    private BookingController bookingController;
    @Autowired
    private CustomerController customerController;
    @Autowired
    private RoomController roomController;

    @Test
    public void contextLoads() {
        assertThat(bookingController).isNotNull();
        assertThat(customerController).isNotNull();
        assertThat(roomController).isNotNull();
    }
}
