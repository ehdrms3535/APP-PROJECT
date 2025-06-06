package com.example.demo.entity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="MapConfig")
public class MapConfig {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;              // 저장할 맵 이름

    @Lob
    @Column(columnDefinition = "TEXT")
    private String geoJson;           // GeoJSON 문자열

    private LocalDateTime createdAt;  // 생성 시각

    public MapConfig() {}

    public MapConfig(String name, String geoJson) {
        this.name = name;
        this.geoJson = geoJson;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGeoJson() {
        return geoJson;
    }

    public void setGeoJson(String geoJson) {
        this.geoJson = geoJson;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
