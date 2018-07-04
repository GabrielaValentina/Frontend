package com.example.gabri.licentafrontend.Domain;

/**
 * Created by gabri on 4/27/2018.
 */

public class RouteSearched {

    private String id_train;
    private String departure_station;
    private String arrival_station;
    private String departure_hour;
    private String arrival_hour;
    private String total_duration;
    private int routeNumber;

    public RouteSearched(){

    }

    public RouteSearched(String id_train, String departure_station, String arrival_station, String departure_hour, String arrival_hour, String total_duration, int routeNumber) {
        this.id_train = id_train;
        this.departure_station = departure_station;
        this.arrival_station = arrival_station;
        this.departure_hour = departure_hour;
        this.arrival_hour = arrival_hour;
        this.total_duration = total_duration;
        this.routeNumber = routeNumber;
    }

    public int getRouteNumber() {
        return routeNumber;
    }

    public void setRouteNumber(int routeNumber) {
        this.routeNumber = routeNumber;
    }

    public String getId_train() {
        return id_train;
    }

    public void setId_train(String id_train) {
        this.id_train = id_train;
    }

    public String getDeparture_station() {
        return departure_station;
    }

    public void setDeparture_station(String departure_station) {
        this.departure_station = departure_station;
    }

    public String getArrival_station() {
        return arrival_station;
    }

    public void setArrival_station(String arrival_station) {
        this.arrival_station = arrival_station;
    }

    public String getDeparture_hour() {
        return departure_hour;
    }

    public void setDeparture_hour(String departure_hour) {
        this.departure_hour = departure_hour;
    }

    public String getArrival_hour() {
        return arrival_hour;
    }

    public void setArrival_hour(String arrival_hour) {
        this.arrival_hour = arrival_hour;
    }

    public String getTotal_duration() {
        return total_duration;
    }

    public void setTotal_duration(String total_duration) {
        this.total_duration = total_duration;
    }

}
