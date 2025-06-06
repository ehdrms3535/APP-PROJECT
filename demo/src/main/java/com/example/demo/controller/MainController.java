package com.example.demo.controller;

import com.example.demo.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {
    @GetMapping("/map")
    public String showMap() {
        return "map";  // templates/map.html
    }

    @GetMapping("/group")
    public String showGroup() {
        return "group"; // templates/group.html
    }

    @GetMapping("/save")
    public String showSave() {
        return "save"; // templates/save.html
    }
}
