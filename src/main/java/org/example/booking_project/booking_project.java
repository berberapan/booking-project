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
import java.time.LocalDateTime;

@SpringBootApplication
public class booking_project {

    public static void main(String[] args) {
        SpringApplication.run(booking_project.class, args);

    }
    /*
    @Bean
    public CommandLineRunner saveBooking(BookingRepo repo, CustomerRepo customerRepo, RoomRepo roomRepo){
        return (args) -> {
            Customer customer1 = customerRepo.findById(1L).orElse(null);
            Customer customer2 = customerRepo.findById(2L).orElse(null);
            Customer customer3 = customerRepo.findById(3L).orElse(null);
            Customer customer4 = customerRepo.findById(4L).orElse(null);

            Room room1 = roomRepo.findById(1L).orElse(null);
            Room room2 = roomRepo.findById(2L).orElse(null);
            Room room3 = roomRepo.findById(3L).orElse(null);
            Room room4 = roomRepo.findById(4L).orElse(null);

            repo.save(new Booking(null,"1",customer1,room1,1,LocalDate.now(),LocalDate.now()));
            repo.save(new Booking(null,"2",customer2,room2,1,LocalDate.now(),LocalDate.now()));
            repo.save(new Booking(null,"3",customer3,room3,1,LocalDate.now(),LocalDate.now()));
            repo.save(new Booking(null,"4",customer4,room4,1,LocalDate.now(),LocalDate.now()));
        };
    }

    @Bean
    public CommandLineRunner saveRooms(RoomRepo repo) {
        return (args) -> {
            repo.save(new Room(null, 101, RoomType.SINGLE, 1, 600));
            repo.save(new Room(null, 102, RoomType.SINGLE, 1, 600));
            repo.save(new Room(null, 103, RoomType.SINGLE, 1, 400));
            repo.save(new Room(null, 104, RoomType.SINGLE, 1, 400));
            repo.save(new Room(null, 201, RoomType.DOUBLE, 3, 1000));
            repo.save(new Room(null, 202, RoomType.DOUBLE, 3, 1000));
            repo.save(new Room(null, 203, RoomType.DOUBLE, 4, 1200));
            repo.save(new Room(null, 301, RoomType.DOUBLE, 3, 1000));
            repo.save(new Room(null, 302, RoomType.DOUBLE, 4, 1200));
            repo.save(new Room(null, 303, RoomType.DOUBLE, 4, 1200));
        };
    }

    @Bean
    public CommandLineRunner saveCustomer(CustomerRepo repo){
        return (args) -> {
            repo.save(new Customer(null,"1","Harald Karlsson","0735321728",
                    "email@email.com"));
            repo.save(new Customer(null,"2","Peter Lingon","0705264826",
                    "email2@email.com"));
            repo.save(new Customer(null,"3","Frans Gustavsson","0765020124",
                    "email3@email.com"));
            repo.save(new Customer(null,"4","Helena Ahlen","0731923729",
                    "email4@email.com"));
        };
    }

     */


}
