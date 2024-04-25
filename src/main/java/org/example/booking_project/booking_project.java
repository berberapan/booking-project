package org.example.booking_project;

import org.example.booking_project.models.Room;
import org.example.booking_project.models.RoomType;
import org.example.booking_project.repos.RoomRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class booking_project {

    public static void main(String[] args) {
        SpringApplication.run(booking_project.class, args);

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
}
