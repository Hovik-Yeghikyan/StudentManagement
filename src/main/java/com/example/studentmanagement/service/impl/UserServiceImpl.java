package com.example.studentmanagement.service.impl;

import com.example.studentmanagement.entity.User;
import com.example.studentmanagement.entity.UserType;
import com.example.studentmanagement.repository.UserRepository;
import com.example.studentmanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    @Value("${picture.upload.directory}")
    private String uploadDirectory;


    @Override
    public User save(User user, MultipartFile multipartFile) throws IOException {
        if (multipartFile != null && !multipartFile.isEmpty()) {
            String picName = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
            File file = new File(uploadDirectory, picName);
            multipartFile.transferTo(file);
            user.setPicName(picName);
        }
        return userRepository.save(user);
    }

    @Override
    public User update(User user, MultipartFile multipartFile) throws IOException {
        if (multipartFile != null && !multipartFile.isEmpty()) {
            String picName = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
            File file = new File(uploadDirectory, picName);
            multipartFile.transferTo(file);
            user.setPicName(picName);
        } else {
            Optional<User> fromDB = userRepository.findById(user.getId());
            user.setPicName(fromDB.get().getPicName());
        }
        return userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> findAllByUserType(UserType userType) {
        return userRepository.findAllByUserType(userType);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void deleteById(int id) {
        userRepository.deleteById(id);

    }

    @Override
    public void deleteImage(int userId) {
        Optional<User> byId = userRepository.findById(userId);
        User user = byId.get();
        String picName = user.getPicName();
        if (picName != null) {
            user.setPicName(null);
            userRepository.save(user);
            File file = new File(uploadDirectory, picName);
            if (file.exists()) {
                file.delete();
            }
        }
    }
}
