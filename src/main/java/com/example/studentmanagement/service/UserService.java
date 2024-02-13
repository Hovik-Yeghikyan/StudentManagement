package com.example.studentmanagement.service;

import com.example.studentmanagement.entity.User;
import com.example.studentmanagement.entity.UserType;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface UserService {

    User save(User user, MultipartFile multipartFile) throws IOException;

    User update(User user, MultipartFile multipartFile) throws IOException;

    List<User> findAll();

    Optional<User> findById(int id);

    List<User> findAllByUserType(UserType userType);

    Optional<User> findByEmail(String email);


    void deleteById(int id);

    void deleteImage(int userId);


}
