package org.example.booking_project;

import org.example.booking_project.models.Room;
import org.example.booking_project.models.RoomType;
import org.example.booking_project.repos.RoomRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Objects;

@SpringBootApplication
public class booking_project {

    public static void main(String[] args) {
        if (args.length == 0) {
            SpringApplication.run(booking_project.class, args);

        } else if (Objects.equals(args[0], "fetchshippers")) {
            SpringApplication fetchShippersApp = new SpringApplication(FetchShippers.class);
            fetchShippersApp.setWebApplicationType(WebApplicationType.NONE);
            fetchShippersApp.run(args);

        } else if (Objects.equals(args[0], "fetchcustomers")) {
            SpringApplication fetchShippersApp = new SpringApplication(FetchCustomers.class);
            fetchShippersApp.setWebApplicationType(WebApplicationType.NONE);
            fetchShippersApp.run(args);
        }
    }
/*
        @Bean
        public CommandLineRunner init(TemplateSeeder templateSeeder) {
            return args -> {
                // Köra seedTemplates-metoden vid starten av applikationen
                templateSeeder.seedTemplate();
            };
    }

/*
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
*/
/*
    @Bean
    public CommandLineRunner saveBooking(BookingRepo repo, CustomerRepo customerRepo, RoomRepo roomRepo){
        return (args) -> {

            Customer customer1 = customerRepo.findById(1L).orElse(null);
            Customer customer2 = customerRepo.findById(2L).orElse(null);
            Customer customer3 = customerRepo.findById(3L).orElse(null);
            Customer customer4 = customerRepo.findById(4L).orElse(null);


            Room r1 = new Room(null, 101, RoomType.SINGLE, 1, 600);
            Room r2 = new Room(null, 102, RoomType.SINGLE, 1, 600);
            Room r3 = new Room(null, 103, RoomType.SINGLE, 1, 400);
            Room r4 = new Room(null, 104, RoomType.SINGLE, 1, 400);
/*
            roomRepo.save(r1);
            roomRepo.save(r2);
            roomRepo.save(r3);
            roomRepo.save(r4);
            Customer c1 = new Customer(null, "adsf", "Andy", "04564054", "adf@adfs.com");
            Customer c2 = new Customer(null, "124421", "Beata", "04564054324", "adfi2@adfs.com");


            roomRepo.save(new Room(null, 201, RoomType.DOUBLE, 3, 1000));
            roomRepo.save(new Room(null, 202, RoomType.DOUBLE, 3, 1000));
            roomRepo.save(new Room(null, 203, RoomType.DOUBLE, 4, 1200));
            roomRepo.save(new Room(null, 301, RoomType.DOUBLE, 3, 1000));
            roomRepo.save(new Room(null, 302, RoomType.DOUBLE, 4, 1200));
            roomRepo.save(new Room(null, 303, RoomType.DOUBLE, 4, 1200));



            repo.save(new Booking(null, "BN123", c1, r1, 1, LocalDate.of(2024, 5, 1), LocalDate.of(2024, 5, 4) ));
            repo.save(new Booking(null, "BN124", c2, r2, 1, LocalDate.of(2024, 5, 1), LocalDate.of(2024, 5, 6) ));
            repo.save(new Booking(null, "BN125", c2, r3, 1, LocalDate.of(2024, 5, 1), LocalDate.of(2024, 5, 6) ));
            repo.save(new Booking(null, "BN126", c2, r4, 1, LocalDate.of(2024, 5, 1), LocalDate.of(2024, 5, 6) ));


            customerRepo.save(new Customer(null,"1","Harald Karlsson","0735321728", "email@email.com"));
            customerRepo.save(new Customer(null,"2","Peter Lingon","0705264826", "email2@email.com"));
            customerRepo.save(new Customer(null,"3","Frans Gustavsson","0765020124", "email3@email.com"));
            customerRepo.save(new Customer(null,"4","Helena Ahlen","0731923729", "email4@email.com"));

        };


    }

*/
}
