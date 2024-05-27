package org.example.booking_project.service.impl;

import org.example.booking_project.models.ConcreteUserDetails;
import org.example.booking_project.models.User;
import org.example.booking_project.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.getUserByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new ConcreteUserDetails(user);
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
