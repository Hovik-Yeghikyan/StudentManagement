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
    @ManyToOne(fetch = FetchType.EAGER)
    private User fromId;
    @ManyToOne(fetch = FetchType.EAGER)
    private User toId;

}
