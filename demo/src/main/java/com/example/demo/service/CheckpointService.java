// CheckpointService.java
package com.example.demo.service;

import com.example.demo.entity.Checkpoint;
import com.example.demo.repository.CheckpointRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CheckpointService {

    private final com.example.demo.repository.CheckpointRepository checkpointRepository;

    public CheckpointService(CheckpointRepository checkpointRepository) {
        this.checkpointRepository = checkpointRepository;
    }

    public List<Checkpoint> getAllCheckpoints() {
        return checkpointRepository.findAll();
    }

    public Checkpoint createCheckpoint(double latitude, double longitude,
                                       String name, Integer number) {
        Checkpoint cp = new Checkpoint(latitude, longitude, name, number);
        return checkpointRepository.save(cp);
    }
}
