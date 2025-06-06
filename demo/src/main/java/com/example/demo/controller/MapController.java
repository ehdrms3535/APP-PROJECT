package com.example.demo.controller;

import com.example.demo.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MapController {
    @GetMapping("/loadmap")
    public String loadMap() {
        return "loadmap";  // templates/map.html
    }

    @GetMapping("/checkpoint")
    public String checkPoint() {
        return "checkpoint"; // templates/group.html
    }

    @GetMapping("/reference")
    public String reference() {
        return "reference";
    }

}
