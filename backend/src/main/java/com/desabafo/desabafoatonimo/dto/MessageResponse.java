package com.desabafo.desabafoatonimo.dto;

import com.desabafo.desabafoatonimo.model.Message;

import java.time.LocalDateTime;

public class MessageResponse {

    private Long id;
    private String content;
    private LocalDateTime createdAt;

    // Construtores
    public MessageResponse() {}

    public MessageResponse(Long id, String content, LocalDateTime createdAt) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
    }

    // Método estático para converter de Entity
    public static MessageResponse fromEntity(Message message) {
        return new MessageResponse(
                message.getId(),
                message.getContent(),
                message.getCreatedAt()
        );
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}