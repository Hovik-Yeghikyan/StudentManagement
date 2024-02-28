package com.example.studentmanagement.repository;

import com.example.studentmanagement.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Integer> {
//List<Message> findAllByFromIdAndAndToId(int fromId,int toId);
}
