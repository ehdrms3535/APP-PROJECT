package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "map")  // 실제 테이블명이 다르면 여기에 지정
public class Map {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double latitude;
    private double longitude;
    private boolean check;
    private boolean refer;
    private String checkname;
    private String refername;
    private int checkpoint;
    private int referpoint;

    // JPA는 기본 생성자가 필요합니다.
    public Map() {}

    // 편의 생성자 (필요한 필드에 따라 시그니처를 바꾸세요)
    public Map(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // --- getters & setters ---
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public boolean isCheck() {
        return check;
    }
    public void setCheck(boolean check) {
        this.check = check;
    }

    public boolean isRefer() {
        return refer;
    }
    public void setRefer(boolean refer) {
        this.refer = refer;
    }

    public String getCheckname() {
        return checkname;
    }
    public void setCheckname(String checkname) {
        this.checkname = checkname;
    }

    public String getRefername() {
        return refername;
    }
    public void setRefername(String refername) {
        this.refername = refername;
    }

    public int getCheckpoint() {
        return checkpoint;
    }
    public void setCheckpoint(int checkpoint) {
        this.checkpoint = checkpoint;
    }

    public int getReferpoint() {
        return referpoint;
    }
    public void setReferpoint(int referpoint) {
        this.referpoint = referpoint;
    }
}
