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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Objects;
import java.time.LocalDateTime;


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
        return User.builder().username(userDTO.getUsername()).password(encodePassword(userDTO.getPassword())).roles(roles).build();
    }

    public void updateUser(UserDTO userDTO) {

        User existingUser = userRepo.findById(userDTO.getId()).orElse(null);

        if (existingUser != null) {
            ArrayList<Role> roles = getRolesAsList(userDTO);

            existingUser.setUsername(userDTO.getUsername());
            existingUser.setRoles(roles);

            userRepo.save(existingUser);
        }
    }

    public ArrayList<Role> getRolesAsList(UserDTO user) {
        ArrayList<Role> roles = new ArrayList<>();
        if (user.isAdmin()) roles.add(roleRepo.findByName("admin"));
        if (user.isReceptionist()) roles.add(roleRepo.findByName("receptionist"));
        return roles;
    }

    public String encodePassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
    
    public void setNewResetPasswordToken(String resetPasswordToken, String username) throws UsernameNotFoundException {
        User user = userRepo.getUserByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        } else {
            user.setResetPasswordToken(resetPasswordToken);
            user.setResetPasswordExpiration(LocalDateTime.now().plusHours(24));
            userRepo.save(user);
        }
    }

    public User getUserByToken(String resetPasswordToken) {
        return userRepo.findByResetPasswordToken(resetPasswordToken);
    }

    public void updatePassword(User user, String newPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(newPassword);
        user.setPassword(encodedPassword);
        user.setResetPasswordToken(null);
        userRepo.save(user);

    }
}
