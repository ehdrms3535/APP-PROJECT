package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/chat")
public class ChatController {

    /**
     * /chat/{groupId} 로 들어오면
     * groupId를 모델에 담아 chat.html 뷰를 리턴
     */
    @GetMapping("/{groupId}")
    public String chatPage(@PathVariable Long groupId, Model model) {
        model.addAttribute("groupId", groupId);
        return "chat";
    }
}
