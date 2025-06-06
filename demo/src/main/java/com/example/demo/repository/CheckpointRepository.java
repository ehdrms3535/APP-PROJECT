package com.example.demo.repository;

import com.example.demo.entity.Checkpoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

public interface CheckpointRepository extends JpaRepository<Checkpoint, Long> {
    List<Checkpoint> findAllByDeletedFalse();
}

