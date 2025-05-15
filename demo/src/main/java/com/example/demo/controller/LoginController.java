package com.example.demo.controller;

import com.example.demo.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class LoginController {

    private final AuthService auth;

    public LoginController(AuthService auth) {
        this.auth = auth;
    }

    // 🔹 HTML 방식 회원가입 페이지 렌더링
    @GetMapping("/register")
    public String showRegister() {
        return "register"; // register.html
    }

    // 🔹 HTML form 전송 방식 회원가입 처리
    @PostMapping("/register")
    public String registerForm(@RequestParam String username,
                               @RequestParam String password,
                               Model model) {
        if (!auth.register(username, password)) {
            model.addAttribute("error", "이미 존재하는 사용자입니다.");
            return "register";
        }
        return "redirect:/login";
    }

    // 🔹 API 방식 회원가입 처리 (AngularJS가 사용)
    @PostMapping("/api/register")
    @ResponseBody
    public ResponseEntity<?> register(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");

        if (!auth.register(username, password)) {
            return ResponseEntity.status(400).body(Map.of("message", "이미 존재하는 사용자입니다."));
        }
        return ResponseEntity.ok(Map.of("message", "회원가입 성공"));
    }

    // 🔹 HTML 로그인 페이지 렌더링
    @GetMapping("/login")
    public String showLogin() {
        return "login"; // login.html
    }

    @PostMapping("/login")
    public String loginForm(@RequestParam String username,
                            @RequestParam String password,
                            Model model) {
        boolean success = auth.login(username, password);
        if (success) {
            return "redirect:/main";  // 로그인 성공 시 메인 페이지로
        } else {
            model.addAttribute("error", "로그인 실패: 잘못된 사용자 정보입니다.");
            return "login";  // 로그인 실패 시 다시 로그인 페이지
        }
    }

    // 🔹 API 방식 로그인 처리 (AngularJS가 사용)
    @PostMapping("/api/login")
    @ResponseBody
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");

        if (auth.login(username, password)) {
            return ResponseEntity.ok(Map.of("message", "로그인 성공"));
        } else {
            return ResponseEntity.status(401).body(Map.of("message", "로그인 실패"));
        }
    }

    // 🔹 로그인 후 메인 페이지
    @GetMapping("/main")
    public String main() {
        return "main"; // main.html
    }
}
