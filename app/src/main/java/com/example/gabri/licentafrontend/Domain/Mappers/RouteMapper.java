package com.example.gabri.licentafrontend.Domain.Mappers;

import java.security.Timestamp;
import java.util.Calendar;
import java.util.List;

/**
 * Created by gabri on 5/13/2018.
 */

public class RouteMapper {
    private Long id;
    private String id_train;
    private Timestamp date;
    private Timestamp delay;
    private List<StationMapper1> stations;

    public RouteMapper(){}

    public RouteMapper(Long id, String id_train, Timestamp date, Timestamp delay, List<StationMapper1> stations) {
        this.id = id;
        this.id_train = id_train;
        this.date = date;
        this.delay = delay;
        this.stations = stations;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getId_train() {
        return id_train;
    }

    public void setId_train(String id_train) {
        this.id_train = id_train;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Timestamp getDelay() {
        return delay;
    }

    public void setDelay(Timestamp delay) {
        this.delay = delay;
    }

    public List<StationMapper1> getStations() {
        return stations;
    }

    public void setStations(List<StationMapper1> stations) {
        this.stations = stations;
    }

    @Override
    public String toString() {
        return "RouteMapper{" +
                "id=" + id +
                ", id_train='" + id_train + '\'' +
                ", date=" + date +
                ", delay=" + delay +
                ", stations=" + stations +
                '}';
    }
}
