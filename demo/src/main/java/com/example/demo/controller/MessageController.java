package com.example.demo.controller;

import com.example.demo.entity.Message;

import org.springframework.web.bind.annotation.*;
import com.example.demo.service.MessageService;


import java.util.List;

// MessageController.java
//@RestController
@RequestMapping("/api/groups/{id}/messages")
public class MessageController {
    private final MessageService msgService;
    public MessageController(MessageService msgService) { this.msgService = msgService; }

    @GetMapping
    public List<Message> getMessages(@PathVariable("id") Long groupId) {
        return msgService.getMessages(groupId);
    }

    @PostMapping
    public Message postMessage(@PathVariable("id") Long groupId, @RequestBody Message msg) {
        msg.setGroupId(groupId);
        return msgService.save(msg);
    }
}