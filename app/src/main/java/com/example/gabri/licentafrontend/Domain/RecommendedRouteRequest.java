package com.example.gabri.licentafrontend.Domain;

/**
 * Created by gabri on 6/11/2018.
 */

public class RecommendedRouteRequest {
    private Long id_route;
    private String train;
    private String departure;
    private String destination;

    public RecommendedRouteRequest() {
    }

    public RecommendedRouteRequest(Long id_route, String train, String departure, String destination) {
        this.id_route = id_route;
        this.train = train;
        this.departure = departure;
        this.destination = destination;
    }

    public Long getId_route() {
        return id_route;
    }

    public void setId_route(Long id_route) {
        this.id_route = id_route;
    }

    public String getTrain() {
        return train;
    }

    public void setTrain(String train) {
        this.train = train;
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
}
