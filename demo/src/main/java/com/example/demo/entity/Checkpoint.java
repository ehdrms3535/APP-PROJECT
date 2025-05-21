// Checkpoint.java (Entity)
package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "checkpoint", uniqueConstraints = {
        @UniqueConstraint(columnNames = "number")
})
public class Checkpoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true)
    private Integer number;

    // 기본 생성자
    public Checkpoint() {}

    // 전체 필드 생성자
    public Checkpoint(double latitude, double longitude, String name, Integer number) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.number = number;
    }

    // getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getNumber() { return number; }
    public void setNumber(Integer number) { this.number = number; }
}

// CheckpointRepository.java
