package com.example.demo.controller;

import com.example.demo.entity.Reference;
import com.example.demo.repository.ReferenceRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/references")
public class ReferenceController {

    private final ReferenceRepository repo;
    public ReferenceController(ReferenceRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public ResponseEntity<List<Reference>> getAll() {
        return ResponseEntity.ok(repo.findAll());
    }

    @PostMapping
    public ResponseEntity<Reference> create(@RequestBody Reference ref) {
        return ResponseEntity.ok(repo.save(ref));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}