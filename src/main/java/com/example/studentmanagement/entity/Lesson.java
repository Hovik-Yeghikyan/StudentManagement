package com.example.studentmanagement.entity;

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
    private String startDate;
    @OneToMany(mappedBy = "lesson")
    private List<User> teacherId;

}
