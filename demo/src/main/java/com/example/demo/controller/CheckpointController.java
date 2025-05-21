package com.example.demo.controller;

import com.example.demo.entity.Checkpoint;
import com.example.demo.service.CheckpointService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/checkpoints")
public class CheckpointController {

    private final CheckpointService checkpointService;

    public CheckpointController(CheckpointService checkpointService) {
        this.checkpointService = checkpointService;
    }

    @GetMapping
    public ResponseEntity<List<Checkpoint>> getAll() {
        return ResponseEntity.ok(checkpointService.getAllCheckpoints());
    }

    @PostMapping
    public ResponseEntity<Checkpoint> create(@RequestBody CreateCheckpointRequest req) {
        Checkpoint saved = checkpointService.createCheckpoint(
                req.getLatitude(), req.getLongitude(),
                req.getName(), req.getNumber()
        );
        return ResponseEntity.ok(saved);
    }

    public static class CreateCheckpointRequest {
        private double latitude;
        private double longitude;
        private String name;
        private Integer number;

        public CreateCheckpointRequest() {}
        public double getLatitude() { return latitude; }
        public void setLatitude(double latitude) { this.latitude = latitude; }

        public double getLongitude() { return longitude; }
        public void setLongitude(double longitude) { this.longitude = longitude; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public Integer getNumber() { return number; }
        public void setNumber(Integer number) { this.number = number; }
    }
}
