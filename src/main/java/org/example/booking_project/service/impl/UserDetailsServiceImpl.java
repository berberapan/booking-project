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
        ArrayList<Role> roles = getRolesAsList(userDTO);
        return User.builder().username(userDTO.getUsername()).password(userDTO.getPassword()).roles(roles).build();
    }

    public void updateUser( UserDTO userDTO) {

        User existingUser = userRepo.findById(userDTO.getId()).orElse(null);

        if (existingUser != null) {
            ArrayList<Role> roles = getRolesAsList(userDTO);
            System.out.println(existingUser.getRoles().stream().toList());
            existingUser.setUsername(userDTO.getUsername());
            existingUser.setRoles(roles);
            System.out.println(existingUser.getRoles().stream().toList());
            userRepo.save(existingUser);
        }
    }

    public ArrayList<Role> getRolesAsList(UserDTO user){
        ArrayList<Role> roles = new ArrayList<>();
        if (user.isAdmin()) roles.add(roleRepo.findByName("admin"));
        if (user.isReceptionist()) roles.add(roleRepo.findByName("receptionist"));
        return roles;
    }
}
