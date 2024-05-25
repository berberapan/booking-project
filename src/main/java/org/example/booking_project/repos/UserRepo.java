package org.example.booking_project.repos;

import org.example.booking_project.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepo extends JpaRepository<User, UUID> {
    public User getUserByUsername(String username);

    public User findByResetPasswordToken(String resetPasswordToken);
}
