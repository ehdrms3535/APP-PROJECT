// CheckpointService.java
package com.example.demo.service;

import com.example.demo.entity.Checkpoint;
import com.example.demo.repository.CheckpointRepository;
import jakarta.persistence.EntityNotFoundException;
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
        // deleted=false 인 것만 조회
        return checkpointRepository.findAllByDeletedFalse();
    }

    public Checkpoint createCheckpoint(double latitude, double longitude,
                                       String name, Integer number) {
        Checkpoint cp = new Checkpoint(latitude, longitude, name, number);
        return checkpointRepository.save(cp);
    }

    public void delete(Long id) {
        Checkpoint cp = checkpointRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("삭제할 체크포인트가 없습니다. id=" + id)
                );
        cp.setDeleted(true);
        // @Transactional 이므로 이 시점에 UPDATE 쿼리가 실행됩니다.
    }

    public void restore(Long id) {
        Checkpoint cp = checkpointRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("복원할 체크포인트가 없습니다. id=" + id));
        cp.setDeleted(false);
    }

}
