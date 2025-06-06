package com.example.demo.service;

import com.example.demo.entity.RoadInfo;
import com.example.demo.repository.RoadInfoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoadInfoService {
    private final RoadInfoRepository repo;
    public RoadInfoService(RoadInfoRepository repo) { this.repo = repo; }

    public void deleteById(Long id) {
        repo.deleteById(id);
    }
}
