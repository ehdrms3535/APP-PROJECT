// src/main/java/com/example/demo/service/MessageService.java
package com.example.demo.service;

import com.example.demo.entity.Message;
import com.example.demo.repository.MessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    /**
     * 특정 그룹의 메시지 목록을 시간순으로 조회
     */
    public List<Message> getMessages(Long groupId) {
        return messageRepository.findAllByGroupId(groupId);
    }

    /**
     * 새로운 메시지를 저장
     */
    public Message save(Message message) {
        return messageRepository.save(message);
    }


}
