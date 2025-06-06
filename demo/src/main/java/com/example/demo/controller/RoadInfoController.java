package com.example.demo.controller;

import com.example.demo.entity.RoadInfo;
import com.example.demo.repository.RoadInfoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.example.demo.service.RoadInfoService;
import java.util.List;

@RestController
@RequestMapping("/api/roads")
public class RoadInfoController {
    private final RoadInfoRepository repo;
    public RoadInfoController(RoadInfoRepository repo){this.repo=repo;}

    @GetMapping
    public List<RoadInfo> list() { return repo.findAll(); }

    @PostMapping
    public RoadInfo create(@RequestBody RoadInfo ri) {
        return repo.save(ri);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoad(@PathVariable Long id) {
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}