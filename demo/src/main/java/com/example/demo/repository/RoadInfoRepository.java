package com.example.demo.repository;

import com.example.demo.entity.RoadInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface RoadInfoRepository extends JpaRepository<RoadInfo,Long> {}