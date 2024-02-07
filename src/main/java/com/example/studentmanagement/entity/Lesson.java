package com.example.studentmanagement.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "lesson")
@Data
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private double duration;
    private double price;
    @JsonFormat(pattern = "yyyy-MM-DD", shape = JsonFormat.Shape.STRING)
    private String startDate;

    @OneToMany(mappedBy = "lesson")
    private List<User> teacherId;
//    @ManyToOne
//    private User teacherId;

}