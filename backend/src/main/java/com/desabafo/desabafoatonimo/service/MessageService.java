package com.desabafo.desabafoatonimo.service;

import com.desabafo.desabafoatonimo.dto.MessageRequest;
import com.desabafo.desabafoatonimo.dto.MessageResponse;
import com.desabafo.desabafoatonimo.model.Message;
import com.desabafo.desabafoatonimo.model.User;
import com.desabafo.desabafoatonimo.repository.MessageRepository;
import com.desabafo.desabafoatonimo.repository.UserRepository;
import com.desabafo.desabafoatonimo.util.IpHashUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    // Construtor
    public MessageService(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public MessageResponse createMessage(MessageRequest request, String clientIp) {
        String ipHash = IpHashUtil.hashIp(clientIp);

        User user = userRepository.findByIpHash(ipHash)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setIpHash(ipHash);
                    return userRepository.save(newUser);
                });

        Message message = new Message();
        message.setContent(request.getContent());
        message.setUser(user);

        Message savedMessage = messageRepository.save(message);

        return MessageResponse.fromEntity(savedMessage);
    }

    @Transactional(readOnly = true)
    public MessageResponse getRandomMessage() {
        return messageRepository.findRandomMessage()
                .map(MessageResponse::fromEntity)
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public List<MessageResponse> getMyMessages(String clientIp) {
        String ipHash = IpHashUtil.hashIp(clientIp);

        return userRepository.findByIpHash(ipHash)
                .map(user -> user.getMessages().stream()
                        .map(MessageResponse::fromEntity)
                        .collect(Collectors.toList()))
                .orElse(List.of());
    }

    @Transactional(readOnly = true)
    public long getTotalMessages() {
        return messageRepository.count();
    }
}