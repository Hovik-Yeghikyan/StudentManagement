package com.example.studentmanagement.service.impl;

import com.example.studentmanagement.entity.Lesson;
import com.example.studentmanagement.repository.LessonRepository;
import com.example.studentmanagement.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;


    @Override
    public Lesson save(Lesson lesson) {
       return lessonRepository.save(lesson);
    }

    @Override
    public List<Lesson> findAll() {
        return lessonRepository.findAll();
    }

    @Override
    public Optional<Lesson> findById(int id) {
        return lessonRepository.findById(id);
    }

    @Override
    public void deleteById(int id) {
        lessonRepository.deleteById(id);
    }
}
