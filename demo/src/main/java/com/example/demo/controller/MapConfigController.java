package com.example.demo.controller;

import com.example.demo.entity.MapConfig;
import com.example.demo.service.MapConfigService;
import com.example.demo.dto.CreateMapConfigRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mapconfigs")
public class MapConfigController {
    private final MapConfigService service;
    public MapConfigController(MapConfigService service) {
        this.service = service;
    }

    @GetMapping
    public List<MapConfig> list() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public MapConfig get(@PathVariable Long id) {
        return service.get(id);
    }

    @PostMapping
    public ResponseEntity<MapConfig> create(@RequestBody CreateMapConfigRequest req) {
        MapConfig saved = service.save(req.getName(), req.getGeoJson());
        return ResponseEntity.ok(saved);
    }
}