package com.example.gabri.licentafrontend.Domain.Mappers;

/**
 * Created by gabri on 5/13/2018.
 */

public class StationMapper1 {
    private Long id;
    private String name;
    private Long id_route;
    private int station_number_in_route;
    private String arrival_time;
    private String departure_time;

    public  StationMapper1(){}

    public StationMapper1(Long id, String name, Long id_route, int station_number_in_route, String arrival_time, String departure_time) {
        this.id = id;
        this.name = name;
        this.id_route = id_route;
        this.station_number_in_route = station_number_in_route;
        this.arrival_time = arrival_time;
        this.departure_time = departure_time;
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

    public Long getId_route() {
        return id_route;
    }

    public void setId_route(Long id_route) {
        this.id_route = id_route;
    }

    public int getStation_number_in_route() {
        return station_number_in_route;
    }

    public void setStation_number_in_route(int station_number_in_route) {
        this.station_number_in_route = station_number_in_route;
    }

    public String getArrival_time() {
        return arrival_time;
    }

    public void setArrival_time(String arrival_time) {
        this.arrival_time = arrival_time;
    }

    public String getDeparture_time() {
        return departure_time;
    }

    public void setDeparture_time(String departure_time) {
        this.departure_time = departure_time;
    }
}
