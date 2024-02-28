package com.example.studentmanagement.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private String startDate;

    @OneToMany(mappedBy = "lesson",fetch = FetchType.EAGER)
    private List<User> teacherId;

}