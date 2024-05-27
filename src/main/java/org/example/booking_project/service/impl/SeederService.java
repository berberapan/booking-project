package org.example.booking_project.service.impl;

import lombok.AllArgsConstructor;
import org.example.booking_project.models.Room;
import org.example.booking_project.models.RoomType;
import org.example.booking_project.models.User;
import org.example.booking_project.repos.EmailTemplateRepo;
import org.example.booking_project.repos.RoleRepo;
import org.example.booking_project.repos.RoomRepo;
import org.example.booking_project.repos.UserRepo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.example.booking_project.models.Role;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class SeederService {
    UserRepo userRepo;
    RoleRepo roleRepo;
    RoomRepo roomRepo;

    public void userSeed() {
       if (roleRepo.findByName("admin") == null) {
           addRole("admin");
       }
       if (roleRepo.findByName("receptionist") == null) {
           addRole("receptionist");
       }
       if (userRepo.getUserByUsername("admin@admin.ug") == null) {
           addUser("admin@admin.ug", "admin");
       }
       if (userRepo.getUserByUsername("user@user.ug") == null) {
           addUser("user@user.ug", "receptionist");
       }
       //Test syfte
       if (userRepo.getUserByUsername("josef@boukdir.se") == null) {
           addUser("josef@boukdir.se", "admin");
       }
   }

   public void roomSeed() {
       if (roomRepo.count() == 0) {
           addRooms();
       }
   }

   private void addRole(String role) {
       Role userRole = new Role();
       roleRepo.save(Role.builder().name(role).build());
   }

   private void addUser(String email, String group) {
       ArrayList<Role> roles = new ArrayList<>();
       roles.add(roleRepo.findByName(group));

       BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
       String hash = encoder.encode("pass123");
       userRepo.save(User.builder().password(hash).username(email).roles(roles).build());
   }

   private void addRooms() {
       roomRepo.save(new Room(null, 101, RoomType.SINGLE, 1, 600));
       roomRepo.save(new Room(null, 102, RoomType.SINGLE, 1, 600));
       roomRepo.save(new Room(null, 103, RoomType.SINGLE, 1, 400));
       roomRepo.save(new Room(null, 104, RoomType.SINGLE, 1, 400));
       roomRepo.save(new Room(null, 201, RoomType.DOUBLE, 3, 1000));
       roomRepo.save(new Room(null, 202, RoomType.DOUBLE, 3, 1000));
       roomRepo.save(new Room(null, 203, RoomType.DOUBLE, 4, 1200));
       roomRepo.save(new Room(null, 301, RoomType.DOUBLE, 3, 1000));
       roomRepo.save(new Room(null, 302, RoomType.DOUBLE, 4, 1200));
       roomRepo.save(new Room(null, 303, RoomType.DOUBLE, 4, 1200));
   }


}
