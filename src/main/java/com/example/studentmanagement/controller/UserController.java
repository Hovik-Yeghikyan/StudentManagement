package com.example.studentmanagement.controller;

import com.example.studentmanagement.entity.Lesson;
import com.example.studentmanagement.entity.User;
import com.example.studentmanagement.entity.UserType;
import com.example.studentmanagement.repository.LessonRepository;
import com.example.studentmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${picture.upload.directory}")
    private String uploadDirectory;



    @GetMapping("/user/register")
    public String userRegister(@RequestParam(value = "msg", required = false) String msg, ModelMap modelMap) {
        if (msg != null && !msg.isEmpty()) {
            modelMap.addAttribute("msg", msg);
        }
        return "register";
    }

    @PostMapping("/user/register")
    public String userRegister(@ModelAttribute User user, @RequestParam("picture") MultipartFile multipartFile) throws IOException {
        if (multipartFile != null && !multipartFile.isEmpty()) {
            String picName = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
            File file = new File(uploadDirectory, picName);
            multipartFile.transferTo(file);
            user.setPicName(picName);
        }

        Optional<User> byEmail = userRepository.findByEmail(user.getEmail());
        if (byEmail.isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return "redirect:/user/register?msg=User Registered";
        } else {
            return "redirect:/user/register?msg=Email already in use";
        }
    }

    @GetMapping("/students")
    public String userPage(ModelMap modelMap) {
        List<User> users = userRepository.findAllByUserType(UserType.STUDENT);
        modelMap.put("users", users);
        return "students";
    }

    @GetMapping("/teachers")
    public String teachersPage(ModelMap modelMap) {
        List<User> users = userRepository.findAllByUserType(UserType.TEACHER);
        modelMap.put("users", users);
        return "teachers";
    }


    @GetMapping("/students/add")
    public String addStudentPage(ModelMap modelMap) {
        modelMap.addAttribute("users", userRepository.findAllByUserType(UserType.STUDENT));
        List<Lesson> lessons = lessonRepository.findAll();
        modelMap.put("lessons",lessons);
        return "addStudent";
    }


    @PostMapping("/students/add")
    public String addStudent(@ModelAttribute User user,
                             @RequestParam("picture") MultipartFile multipartFile) throws IOException {
        if (multipartFile != null && !multipartFile.isEmpty()) {
            String picName = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
            File file = new File(uploadDirectory, picName);
            multipartFile.transferTo(file);
            user.setPicName(picName);
        }
        userRepository.save(user);
        return "redirect:/students";
    }


//    @GetMapping("/teachers/add")
//    public String addTeachersPage(ModelMap modelMap) {
//        modelMap.addAttribute("users", userRepository.findAllByUserType(UserType.STUDENT));
//        List<Lesson> lessons = lessonRepository.findAll();
//        modelMap.put("lessons",lessons);
//        return "addTeacher";
//    }
//
//    @PostMapping("/teachers/add")
//    public String addTeacher(@ModelAttribute User user,
//                             @RequestParam("picture") MultipartFile multipartFile) throws IOException {
//        if (multipartFile != null && !multipartFile.isEmpty()) {
//            String picName = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
//            File file = new File(uploadDirectory, picName);
//            multipartFile.transferTo(file);
//            user.setPicName(picName);
//        }
//        userRepository.save(user);
//        return "redirect:/teachers";
//    }


    @PostMapping("/students/update")
    public String updateStudent(@ModelAttribute User user,
                                @RequestParam("picture") MultipartFile multipartFile) throws IOException {
        if (multipartFile != null && !multipartFile.isEmpty()) {
            String picName = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
            File file = new File(uploadDirectory, picName);
            multipartFile.transferTo(file);
            user.setPicName(picName);
        }else {
            Optional<User> fromDB = userRepository.findById(user.getId());
            user.setPicName(fromDB.get().getPicName());
        }
        userRepository.save(user);
        return "redirect:/students";
    }

    @GetMapping("/students/update/{id}")
    public String updateStudentPage(@PathVariable("id") int id, ModelMap modelMap) {
        Optional<User> byId = userRepository.findById(id);
        if (byId.isPresent()) {
            modelMap.addAttribute("lessons", lessonRepository.findAll());
            modelMap.addAttribute("user", byId.get());
        } else {
            return "redirect:/students";
        }
        return "updateStudent";
    }

    @GetMapping("/students/image/delete")
    public String deleteStudentImage(@RequestParam("id") int id) {
        Optional<User> byId = userRepository.findById(id);
        if (byId.isEmpty()) {
            return "redirect:/students";
        } else {
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
            return "redirect:/students/update/" + user.getId();
        }
    }


    @GetMapping("/students/delete/{id}")
    public String deleteStudent(@PathVariable("id") int id) {
        userRepository.deleteById(id);
        return "redirect:/students";
    }

    @PostMapping("/teachers/update")
    public String updateTeacher(@ModelAttribute User user,
                                @RequestParam("picture") MultipartFile multipartFile) throws IOException {
        if (multipartFile != null && !multipartFile.isEmpty()) {
            String picName = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
            File file = new File(uploadDirectory, picName);
            multipartFile.transferTo(file);
            user.setPicName(picName);
        }else {
            Optional<User> fromDB = userRepository.findById(user.getId());
            user.setPicName(fromDB.get().getPicName());
        }
        userRepository.save(user);
        return "redirect:/teachers";
    }

    @GetMapping("/teachers/update/{id}")
    public String updateTeacherPage(@PathVariable("id") int id, ModelMap modelMap) {
        Optional<User> byId = userRepository.findById(id);
        if (byId.isPresent()) {
            modelMap.addAttribute("lessons", lessonRepository.findAll());
            modelMap.addAttribute("user", byId.get());
        } else {
            return "redirect:/teachers";
        }
        return "updateTeacher";
    }

    @GetMapping("/teachers/image/delete")
    public String deleteTeacherImage(@RequestParam("id") int id) {
        Optional<User> byId = userRepository.findById(id);
        if (byId.isEmpty()) {
            return "redirect:/teachers";
        } else {
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
            return "redirect:/teachers/update/" + user.getId();
        }
    }


    @GetMapping("/teachers/delete/{id}")
    public String deleteTeacher(@PathVariable("id") int id) {
        userRepository.deleteById(id);
        return "redirect:/teachers";
    }

}