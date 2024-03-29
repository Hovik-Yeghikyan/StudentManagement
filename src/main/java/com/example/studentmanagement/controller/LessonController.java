package com.example.studentmanagement.controller;

import com.example.studentmanagement.entity.Lesson;
import com.example.studentmanagement.repository.LessonRepository;
import com.example.studentmanagement.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class LessonController {


    private final LessonService lessonService;

    @GetMapping("/lessons")
    public String lessonPage(ModelMap modelMap) {
        List<Lesson> lessons = lessonService.findAll();
        modelMap.put("lessons", lessons);
        return "lessons";
    }

    @GetMapping("/studentLessons")
    public String studentLessonPage(ModelMap modelMap) {
        List<Lesson> lessons = lessonService.findAll();
        modelMap.put("lessons", lessons);
        return "studentLessons";
    }

    @GetMapping("/lessons/add")
    public String addLessonPage() {
        return "addLesson";
    }

    @PostMapping("/lessons/add")
    public String addLesson(@ModelAttribute Lesson lesson) {
        lessonService.save(lesson);
        return "redirect:/lessons";
    }

    @GetMapping("/lessons/delete/{id}")
    public String deleteLesson(@PathVariable("id") int id) {
        lessonService.deleteById(id);
        return "redirect:/lessons";
    }

    @GetMapping("/lessons/update/{id}")
    public String updateLessonPage(@PathVariable("id") int id, ModelMap modelMap) {
        Optional<Lesson> byId = lessonService.findById(id);
        modelMap.addAttribute("lesson", byId.get());
        return "/updateLesson";
    }
    @PostMapping("/lessons/update")
    public String updateLesson(@ModelAttribute Lesson lesson){
        lessonService.findById(lesson.getId());
        lessonService.save(lesson);
        return "redirect:/lessons";
    }






}