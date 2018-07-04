package com.example.gabri.licentafrontend.Domain;

/**
 * Created by gabri on 7/3/2018.
 */
public class SearchedRoute {
    private Long id_route;
    private String departure;
    private String arrival;
    private String arrival_time;
    private String departure_time;
    private String train;

    public SearchedRoute(){}

    public SearchedRoute(Long id_route, String departure, String arrival, String arrival_time, String departure_time, String train) {
        this.id_route = id_route;
        this.departure = departure;
        this.arrival = arrival;
        this.arrival_time = arrival_time;
        this.departure_time = departure_time;
        this.train = train;
    }

    public Long getId_route() {
        return id_route;
    }

    public void setId_route(Long id_route) {
        this.id_route = id_route;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
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

    public String getTrain() {
        return train;
    }

    public void setTrain(String train) {
        this.train = train;
    }
}
