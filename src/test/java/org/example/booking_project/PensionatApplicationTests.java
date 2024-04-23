package org.example.booking_project;

import org.example.booking_project.controllers.BookingController;
import org.example.booking_project.controllers.CustomerController;
import org.example.booking_project.controllers.RoomController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BookingProjectApplicationTests {

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
