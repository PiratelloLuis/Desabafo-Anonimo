package com.desabafo.desabafoatonimo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class MessageRequest {

    @NotBlank(message = "O conteúdo não pode estar vazio")
    @Size(max = 500, message = "O desabafo deve ter no máximo 500 caracteres")
    private String content;

    // Construtores
    public MessageRequest() {}

    public MessageRequest(String content) {
        this.content = content;
    }

    // Getters e Setters
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}