package com.example.gabri.licentafrontend.Domain.Mappers;

/**
 * Created by gabri on 6/18/2018.
 */

public class LandmarkDetailsMapper {
    private int id;
    private Float latitude;
    private Float longitude;
    private String location;
    private String description;
    private Long route_id;
    private String departure;
    private String destination;

    public LandmarkDetailsMapper(Float latitude, Float longitude, String location, String description, Long route_id, String departure, String destination) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.location = location;
        this.description = description;
        this.route_id = route_id;
        this.departure = departure;
        this.destination = destination;
    }

    public LandmarkDetailsMapper(int id, Float latitude, Float longitude, String location, String description, Long route_id, String departure, String destination) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.location = location;
        this.description = description;
        this.route_id = route_id;
        this.departure = departure;
        this.destination = destination;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getRoute_id() {
        return route_id;
    }

    public void setRoute_id(Long route_id) {
        this.route_id = route_id;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
