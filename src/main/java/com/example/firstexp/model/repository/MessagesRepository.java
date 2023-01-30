package com.example.firstexp.model.repository;

import com.example.firstexp.model.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessagesRepository extends JpaRepository<Message, Long> {
    List<Message> findAllBySentToAndStatus(String sentTo, boolean status);
    List<Message> findAllBySentFrom(String sentFrom);
    Page<Message> findAllBySentToAndStatus(String sentTo, boolean status, Pageable pageable);
    Page<Message> findAllBySentFrom(String sentFrom, Pageable pageable);
}
