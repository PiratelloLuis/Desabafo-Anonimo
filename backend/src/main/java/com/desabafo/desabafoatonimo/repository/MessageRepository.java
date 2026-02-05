package com.desabafo.desabafoatonimo.repository;

import com.desabafo.desabafoatonimo.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    
    List<Message> findByUserIdOrderByCreatedAtDesc(Long userId);
    
    @Query(value = "SELECT * FROM messages ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Optional<Message> findRandomMessage();
    
    long count();
}
