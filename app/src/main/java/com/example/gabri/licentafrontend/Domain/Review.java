package com.example.gabri.licentafrontend.Domain;

import java.sql.Timestamp;

/**
 * Created by gabri on 4/30/2018.
 */

public class Review {
    private int id;
    private Long id_user;
    private String userName;
    private String comment;
    private Double numberOfStars;
    private String review_type;
    private String details;
    private String time;

    public Review() {
    }

    public Review(Long id_user, String name, String comment, Double numberOfStars, String review_type, String review_param, String time) {
        this.id_user = id_user;
        this.userName = name;
        this.comment = comment;
        this.numberOfStars = numberOfStars;
        this.review_type = review_type;
        this.details = review_param;
        this.time = time;
    }

    public Review(int id, String name,String comment, Double numberOfStars, String review_type, String review_param, String time) {
        this.id = id;
        //this.email_address = email_address;
        this.userName = name;
        this.comment = comment;
        this.numberOfStars = numberOfStars;
        this.review_type = review_type;
        this.details = review_param;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getId_user() {
        return id_user;
    }

    public void setId_user(Long id_user) {
        this.id_user = id_user;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Double getNumberOfStars() {
        return numberOfStars;
    }

    public void setNumberOfStars(Double numberOfStars) {
        this.numberOfStars = numberOfStars;
    }

    public String getReview_type() {
        return review_type;
    }

    public void setReview_type(String review_type) {
        this.review_type = review_type;
    }

    public String getReview_param() {
        return details;
    }

    public void setReview_param(String review_param) {
        this.details = review_param;
    }

    public String getName() {
        return userName;
    }

    public void setName(String name) {
        this.userName = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
