package com.example.studentmanagement.controller;

import com.example.studentmanagement.entity.Lesson;
import com.example.studentmanagement.repository.LessonRepository;
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
public class LessonController {

    @Autowired
    private LessonRepository lessonRepository;

    @GetMapping("/lessons")
    public String lessonPage(ModelMap modelMap) {
        List<Lesson> lessons = lessonRepository.findAll();
        modelMap.put("lessons", lessons);
        return "lessons";
    }

    @GetMapping("/lessons/add")
    public String addLessonPage() {
        return "addLesson";
    }

    @PostMapping("/lessons/add")
    public String addLesson(@ModelAttribute Lesson lesson) {
        lessonRepository.save(lesson);
        return "redirect:/lessons";
    }

    @GetMapping("/lessons/delete/{id}")
    public String deleteLesson(@PathVariable("id") int id) {
        lessonRepository.deleteById(id);
        return "redirect:/lessons";
    }

    @GetMapping("/lessons/update/{id}")
    public String updateLessonPage(@PathVariable("id") int id, ModelMap modelMap) {
        Optional<Lesson> byId = lessonRepository.findById(id);
            modelMap.addAttribute("lesson", byId.get());
        return "/updateLesson";
    }
    @PostMapping("/lessons/update")
    public String updateLesson(@ModelAttribute Lesson lesson){
        lessonRepository.findById(lesson.getId());
        lessonRepository.save(lesson);
        return "redirect:/lessons";
    }






}



