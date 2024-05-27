package org.example.booking_project.service.impl;

import org.example.booking_project.Dtos.UserDTO;
import org.example.booking_project.models.ConcreteUserDetails;
import org.example.booking_project.models.Role;
import org.example.booking_project.models.User;
import org.example.booking_project.repos.RoleRepo;
import org.example.booking_project.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.getUserByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return new ConcreteUserDetails(user);
    }

    public UserDTO userToUserDTO(User user) {
        return UserDTO.builder().id(user.getId()).username(user.getUsername())
                .admin(user.getRoles().stream().anyMatch(role -> Objects.equals(role.getName(), "admin")))
                .receptionist(user.getRoles().stream().anyMatch(role -> Objects.equals(role.getName(), "receptionist")))
                .build();
    }

    public User userDTOToUser(UserDTO userDTO) {
        ArrayList<Role> roles = new ArrayList<>();

        if (userDTO.getAdmin()) roles.add(roleRepo.findByName("admin"));
        if (userDTO.getReceptionist()) roles.add(roleRepo.findByName("receptionist"));

        return User.builder().username(userDTO.getUsername()).password(userDTO.getPassword()).roles(roles).build();
    }


}
