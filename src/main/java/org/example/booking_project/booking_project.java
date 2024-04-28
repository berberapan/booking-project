package org.example.booking_project;

import org.example.booking_project.models.Booking;
import org.example.booking_project.models.Customer;
import org.example.booking_project.models.Room;
import org.example.booking_project.models.RoomType;
import org.example.booking_project.repos.BookingRepo;
import org.example.booking_project.repos.CustomerRepo;
import org.example.booking_project.repos.RoomRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class booking_project {

    public static void main(String[] args) {
        SpringApplication.run(booking_project.class, args);

    }

/*
    @Bean
    public CommandLineRunner saveRooms(RoomRepo repo, BookingRepo repo2, CustomerRepo repo3) {
        return (args) -> {

            Room r1 = new Room(null, 102, RoomType.SINGLE, 1, 600);
            Room r2 = new Room(null, 101, RoomType.SINGLE, 1, 600);
            Room r3 = new Room(null, 103, RoomType.SINGLE, 1, 400);
            Room r4 = new Room(null, 104, RoomType.SINGLE, 1, 400);

            repo.save(r1);
            repo.save(r2);
            repo.save(r3);
            repo.save(r4);

            Customer c1 = new Customer(null, "adsf", "Andy", "04564054", "adf@adfs.com");
            Customer c2 = new Customer(null, "124421", "Beata", "04564054324", "adfi2@adfs.com");

            repo3.save(c1);
            repo3.save(c2);

            repo.save(new Room(null, 201, RoomType.DOUBLE, 3, 1000));
            repo.save(new Room(null, 202, RoomType.DOUBLE, 3, 1000));
            repo.save(new Room(null, 203, RoomType.DOUBLE, 4, 1200));
            repo.save(new Room(null, 301, RoomType.DOUBLE, 3, 1000));
            repo.save(new Room(null, 302, RoomType.DOUBLE, 4, 1200));
            repo.save(new Room(null, 303, RoomType.DOUBLE, 4, 1200));

            repo2.save(new Booking(null, "a", c1, r1, 1, LocalDate.of(2024, 5, 1), LocalDate.of(2024, 5, 4) ));
            repo2.save(new Booking(null, "adfsd", c2, r2, 1, LocalDate.of(2024, 5, 1), LocalDate.of(2024, 5, 6) ));
            repo2.save(new Booking(null, "asd", c2, r3, 1, LocalDate.of(2024, 5, 1), LocalDate.of(2024, 5, 6) ));
            repo2.save(new Booking(null, "asd", c2, r4, 1, LocalDate.of(2024, 5, 1), LocalDate.of(2024, 5, 6) ));

        };
    }
*/

}
