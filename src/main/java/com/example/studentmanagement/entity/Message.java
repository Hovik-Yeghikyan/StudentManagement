package com.example.studentmanagement.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "message")
@Data
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String message;
    private int fromId;
    @ManyToOne
    private User toId;
    @JsonFormat(pattern = "yyyy-MM-DD", shape = JsonFormat.Shape.STRING)
    private String dateTime;
}
