package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.service.AuthService;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /** 회원가입 API */
    @PostMapping("/register")
    public ResponseEntity<?> apiRegister(
            @RequestBody Map<String, String> req) {

        String username = req.get("username");
        String password = req.get("password");

        if (!authService.register(username, password)) {
            return ResponseEntity
                    .badRequest()
                    .body(Collections.singletonMap("message", "이미 존재하는 사용자입니다."));
        }
        return ResponseEntity
                .ok(Collections.singletonMap("message", "회원가입 성공"));
    }

    /** 로그인 API */
    @PostMapping("/login")
    public ResponseEntity<?> apiLogin(
            @RequestBody Map<String, String> creds,
            HttpSession session) {

        String username = creds.get("username");
        String password = creds.get("password");

        if (!authService.login(username, password)) {
            return ResponseEntity
                    .status(401)
                    .body(Collections.singletonMap("message", "로그인 실패"));
        }
        session.setAttribute("username", username);
        return ResponseEntity
                .ok(Collections.singletonMap("username", username));
    }

    /** 현재 로그인 사용자 조회 API */
    @GetMapping("/me")
    public ResponseEntity<?> me(HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return ResponseEntity
                    .status(401)
                    .body(Collections.singletonMap("message", "비로그인 상태"));
        }
        return ResponseEntity
                .ok(Collections.singletonMap("username", username));
    }

    @GetMapping("/main")
    public String main() {
        return "main";
    }
}
