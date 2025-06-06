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

    // Controller(GET)
    @GetMapping
    public List<Checkpoint> getAll() {
        return checkpointService.getAllCheckpoints(); // 내부에서 findAllByDeletedFalse()
    }

    @PostMapping
    public ResponseEntity<Checkpoint> create(@RequestBody CreateCheckpointRequest req) {
        Checkpoint saved = checkpointService.createCheckpoint(
                req.getLatitude(), req.getLongitude(),
                req.getName(), req.getNumber()
        );
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        checkpointService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // 복원용 엔드포인트
    @PutMapping("/{id}/restore")
    public ResponseEntity<Void> restore(@PathVariable Long id) {
        checkpointService.restore(id);
        return ResponseEntity.noContent().build();
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
