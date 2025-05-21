package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.demo.service.AuthService;

@Controller
public class LoginController {

    private final AuthService auth;

    public LoginController(AuthService auth) {
        this.auth = auth;
    }

    // 뷰용: 회원가입 화면
    @GetMapping("/register")
    public String showRegister() {
        return "register";
    }

    // 뷰용: 회원가입 처리 (폼)
    @PostMapping("/register")
    public String registerForm(
            @RequestParam String username,
            @RequestParam String password,
            Model model) {

        if (!auth.register(username, password)) {
            model.addAttribute("error", "이미 존재하는 사용자입니다.");
            return "register";
        }
        return "redirect:/login";
    }

    // 뷰용: 로그인 화면
    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }

    // 뷰용: 로그인 처리 (폼) — 세션에 username 저장
    @PostMapping("/login")
    public String loginForm(
            @RequestParam String username,
            @RequestParam String password,
            HttpSession session,
            Model model) {

        if (!auth.login(username, password)) {
            model.addAttribute("error", "로그인 실패: 잘못된 사용자 정보입니다.");
            return "login";
        }
        // 로그인 성공 시 세션에 username 저장
        session.setAttribute("username", username);
        return "redirect:/main";
    }

    // 뷰용: 메인 페이지
    @GetMapping("/main")
    public String main() {
        return "main";
    }
}
