package com.example.demo.service;

import com.example.demo.entity.MapConfig;
import com.example.demo.repository.MapConfigRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MapConfigService {
    private final MapConfigRepository repo;
    public MapConfigService(MapConfigRepository repo) {
        this.repo = repo;
    }

    public List<MapConfig> getAll() {
        return repo.findAll();
    }

    public MapConfig save(String name, String geoJson) {
        MapConfig cfg = new MapConfig(name, geoJson);
        return repo.save(cfg);
    }

    public MapConfig get(Long id) {
        return repo.findById(id).orElseThrow();
    }
}
