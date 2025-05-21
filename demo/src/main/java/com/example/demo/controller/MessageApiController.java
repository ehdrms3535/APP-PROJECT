package com.example.demo.controller;

import com.example.demo.dto.MessageDto;
import com.example.demo.entity.Message;
import com.example.demo.repository.MessageRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/groups/{groupId}/messages")
public class MessageApiController {

    private final MessageRepository repo;

    public MessageApiController(MessageRepository repo) {
        this.repo = repo;
    }

    // 기존에 만들어 둔 GET 핸들러
    @GetMapping
    public List<MessageDto> list(@PathVariable Long groupId) {
        return repo.findAllByGroupId(groupId).stream()
                .map(m -> new MessageDto(m.getSender(), m.getText()))
                .collect(Collectors.toList());
    }

    // 여기에 POST 핸들러를 넣으세요
    @PostMapping
    public MessageDto post(
            @PathVariable Long groupId,
            @RequestBody MessageDto dto
    ) {
        Message m = new Message();
        m.setGroupId(groupId);
        m.setSender(dto.getSender());   // 세션 대신 클라이언트가 보낸 sender 사용
        m.setText(dto.getText());
        Message saved = repo.save(m);
        return new MessageDto(saved.getSender(), saved.getText());
    }
}
