package com.example.studentmanagement.controller;

import com.example.studentmanagement.entity.Lesson;
import com.example.studentmanagement.entity.Message;
import com.example.studentmanagement.entity.User;
import com.example.studentmanagement.entity.UserType;
import com.example.studentmanagement.repository.MessageRepository;
import com.example.studentmanagement.security.SpringUser;
import com.example.studentmanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class MessageController {
    @Autowired
private MessageRepository messageRepository;
    @Autowired
    private UserService userService;


    @GetMapping("/message")
    public String userPage() {
     return "message";
    }
    @GetMapping("/message/add")
    public String addLessonPage(ModelMap modelMap) {
        List<User> users = userService.findAll();
        modelMap.put("users",users);
        return "addMessage";
    }

    @PostMapping("/message/add")
    public String addLesson(@ModelAttribute Message message) {
       messageRepository.findById(message.getId());
        messageRepository.save(message);
        return "redirect:/message";
    }


}
