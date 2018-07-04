package com.example.gabri.licentafrontend.Domain;

/**
 * Created by gabri on 5/3/2018.
 */

public class Landmark {
    private Long id_landmark;
    private Float latitude;
    private Float longitude;
    private String location;
    private String description;
    private Long route_id;

    public Landmark(Long id, Float latitude, Float longitude, String location, String description, Long id_route) {
        this.id_landmark = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.location = location;
        this.description = description;
        this.route_id = id_route;
    }


    public Landmark(Float latitude, Float longitude, String location, String description, Long id_route) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.location = location;
        this.description = description;
        this.route_id = id_route;
    }

    public Long getId_landmark() {
        return id_landmark;
    }

    public void setId_landmark(Long id_landmark) {
        this.id_landmark = id_landmark;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Long getId_route() {
        return route_id;
    }

    public void setId_route(Long id_route) {
        this.route_id = id_route;
    }
}
