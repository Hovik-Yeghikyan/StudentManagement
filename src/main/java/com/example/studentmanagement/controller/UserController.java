package com.example.studentmanagement.controller;

import com.example.studentmanagement.entity.Lesson;
import com.example.studentmanagement.entity.User;
import com.example.studentmanagement.entity.UserType;
import com.example.studentmanagement.repository.LessonRepository;
import com.example.studentmanagement.security.SpringUser;
import com.example.studentmanagement.service.LessonService;
import com.example.studentmanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;
    private final LessonService lessonService;
    private final PasswordEncoder passwordEncoder;


    @GetMapping("/user/register")
    public String userRegister(@RequestParam(value = "msg", required = false) String msg,
                               ModelMap modelMap) {
        if (msg != null && !msg.isEmpty()) {
            modelMap.addAttribute("msg", msg);
        }
        return "register";
    }

    @PostMapping("/user/register")
    public String userRegister(@ModelAttribute User user, @RequestParam("picture") MultipartFile multipartFile) throws IOException {
        Optional<User> byEmail = userService.findByEmail(user.getEmail());
        if (byEmail.isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.save(user, multipartFile);
            return "redirect:/user/register?msg=User Registered";
        } else {
            return "redirect:/user/register?msg=Email already in use";
        }
    }

    @GetMapping("/loginPage")
    public String loginPage(@AuthenticationPrincipal SpringUser springUser) {
        if (springUser == null) {
            return "loginPage";
        }
        return "redirect:/";
    }

    @GetMapping("/loginSuccess")
    public String loginSuccsess(@AuthenticationPrincipal SpringUser springUser) {
        User user = springUser.getUser();
        if (user != null) {
            return "/loginSuccess";
        }
        return "/";
    }

    @GetMapping("/studentMenu")
    public String studentLessonPage(@AuthenticationPrincipal SpringUser springUser) {
        User user = springUser.getUser();
        if (user.getUserType() == UserType.STUDENT) {
            return "studentMenu";
        }
        return "redirect:/";
    }


    @GetMapping("/students")
    public String userPage(ModelMap modelMap) {
        List<User> users = userService.findAllByUserType(UserType.STUDENT);

        modelMap.put("users", users);
        return "students";
    }


    @GetMapping("/students/add")
    public String addStudentPage(ModelMap modelMap) {
        modelMap.addAttribute("users", userService.findAllByUserType(UserType.STUDENT));
        List<Lesson> lessons = lessonService.findAll();
        modelMap.put("lessons", lessons);
        return "addStudent";
    }


    @PostMapping("/students/add")
    public String addStudent(@ModelAttribute User user,
                             @RequestParam("picture") MultipartFile multipartFile) throws IOException {
        Optional<User> byEmail = userService.findByEmail(user.getEmail());
        if (byEmail.isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.save(user, multipartFile);
            return "redirect:/students/add?msg=User Registered";
        } else {
            return "redirect:/students/add?msg=Email already in use";
        }
    }


    @PostMapping("/students/update")
    public String updateStudent(@ModelAttribute User user,
                                @RequestParam("picture") MultipartFile multipartFile) throws IOException {

        userService.save(user, multipartFile);
        return "redirect:/students";
    }

    @GetMapping("/students/update/{id}")
    public String updateStudentPage(@PathVariable("id") int id, ModelMap modelMap) {
        Optional<User> byId = userService.findById(id);
        if (byId.isPresent()) {
            modelMap.addAttribute("lessons", lessonService.findAll());
            modelMap.addAttribute("user", byId.get());
        } else {
            return "redirect:/students";
        }
        return "updateStudent";
    }

    @GetMapping("/students/image/delete")
    public String deleteStudentImage(@RequestParam("id") int id) {
        Optional<User> byId = userService.findById(id);
        if (byId.isEmpty()) {
            return "redirect:/students";
        } else {
            userService.deleteImage(id);
            return "redirect:/students/update/" + id;
        }

    }


    @GetMapping("/students/delete/{id}")
    public String deleteStudent(@PathVariable("id") int id) {
        userService.deleteById(id);
        return "redirect:/students";
    }


    @GetMapping("/teachers")
    public String teachersPage(ModelMap modelMap) {
        List<User> users = userService.findAllByUserType(UserType.TEACHER);
        modelMap.put("users", users);
        return "teachers";
    }

    @PostMapping("/teachers/update")
    public String updateTeacher(@ModelAttribute User user,
                                @RequestParam("picture") MultipartFile multipartFile) throws IOException {
        userService.update(user, multipartFile);
        return "redirect:/teachers";
    }

    @GetMapping("/teachers/update/{id}")
    public String updateTeacherPage(@PathVariable("id") int id, ModelMap modelMap) {
        Optional<User> byId = userService.findById(id);
        if (byId.isPresent()) {
            modelMap.addAttribute("lessons", lessonService.findAll());
            modelMap.addAttribute("user", byId.get());
        } else {
            return "redirect:/teachers";
        }
        return "updateTeacher";
    }

    @GetMapping("/teachers/image/delete")
    public String deleteTeacherImage(@RequestParam("id") int id) {
        Optional<User> byId = userService.findById(id);
        if (byId.isEmpty()) {
            return "redirect:/employees";
        } else {
            userService.deleteImage(id);
            return "redirect:/teachers/update/" + id;
        }
    }


    @GetMapping("/teachers/delete/{id}")
    public String deleteTeacher(@PathVariable("id") int id) {
        userService.deleteById(id);
        return "redirect:/teachers";
    }

}