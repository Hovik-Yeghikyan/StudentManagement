package com.example.studentmanagement.repository;

import com.example.studentmanagement.entity.User;
import com.example.studentmanagement.entity.UserType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findAllByUserType(UserType userType);

    Optional<User> findByEmail(String email);
    Optional<User> findByToken(String token);
    List<User> findAllByActive(boolean active);
}
