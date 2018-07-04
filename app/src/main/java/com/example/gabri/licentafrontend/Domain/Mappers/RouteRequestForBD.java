package com.example.gabri.licentafrontend.Domain.Mappers;

/**
 * Created by gabri on 6/23/2018.
 */

public class RouteRequestForBD {
    private Long id;
    private String time;
    private String train;

    public RouteRequestForBD(){}

    public RouteRequestForBD(Long id, String time, String train) {
        this.id = id;
        this.time = time;
        this.train = train;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTrain() {
        return train;
    }

    public void setTrain(String train) {
        this.train = train;
    }
}
