package com.example.studentmanagement.controller;

import com.example.studentmanagement.entity.Lesson;
import com.example.studentmanagement.entity.Message;
import com.example.studentmanagement.entity.User;
import com.example.studentmanagement.entity.UserType;
import com.example.studentmanagement.repository.MessageRepository;
import com.example.studentmanagement.security.SpringUser;
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
@RequiredArgsConstructor
public class MessageController {
    @Autowired
private MessageRepository messageRepository;


    @GetMapping("/message")
    public String userPage(@AuthenticationPrincipal SpringUser springUser, ModelMap modelMap) {
        User user = springUser.getUser();
        List<Message> messages = messageRepository.findAll();
        modelMap.put("messages", messages);
     return "message";
    }
    @GetMapping("/message/add")
    public String addLessonPage(@ModelAttribute Message message) {
        messageRepository.save(message);
        return "addMessage";
    }

    @PostMapping("/message/add")
    public String addLesson(@ModelAttribute Message message) {
        messageRepository.save(message);
        return "redirect:/message";
    }


}
