package com.example.demo.dto;

public class CreateMapConfigRequest {
    private String name;
    private String geoJson;
    public CreateMapConfigRequest() {}
    public String getName()   { return name; }
    public void setName(String name) { this.name = name; }
    public String getGeoJson(){ return geoJson; }
    public void setGeoJson(String geoJson) { this.geoJson = geoJson; }
}