package com.example.gabri.licentafrontend.Domain;

import java.io.Serializable;

/**
 * Created by gabri on 4/12/2018.
 */

public class TrainStationAdapter implements Serializable {

    private String station_name;
    private String arrival_time;
    private String departure_time;

    public TrainStationAdapter(){

    }

    public TrainStationAdapter(String station_name, String arrival_time, String departure_time) {
        this.station_name = station_name;
        this.arrival_time = arrival_time;
        this.departure_time = departure_time;
    }

    public String getStation_name() {
        return station_name;
    }

    public void setStation_name(String station_name) {
        this.station_name = station_name;
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
