package com.example.studentmanagement.repository;

import com.example.studentmanagement.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message,Integer> {
}
