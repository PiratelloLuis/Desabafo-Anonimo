package com.desabafo.desabafoatonimo.controller;

import com.desabafo.desabafoatonimo.dto.MessageRequest;
import com.desabafo.desabafoatonimo.dto.MessageResponse;
import com.desabafo.desabafoatonimo.service.MessageService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    // Construtor
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<MessageResponse> createMessage(
            @Valid @RequestBody MessageRequest request,
            HttpServletRequest httpRequest) {

        String clientIp = getClientIp(httpRequest);
        MessageResponse response = messageService.createMessage(request, clientIp);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/random")
    public ResponseEntity<MessageResponse> getRandomMessage() {
        MessageResponse response = messageService.getRandomMessage();

        if (response == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/my")
    public ResponseEntity<List<MessageResponse>> getMyMessages(HttpServletRequest httpRequest) {
        String clientIp = getClientIp(httpRequest);
        List<MessageResponse> messages = messageService.getMyMessages(clientIp);

        return ResponseEntity.ok(messages);
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalMessages", messageService.getTotalMessages());

        return ResponseEntity.ok(stats);
    }

    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");

        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }

        return ip;
    }
}