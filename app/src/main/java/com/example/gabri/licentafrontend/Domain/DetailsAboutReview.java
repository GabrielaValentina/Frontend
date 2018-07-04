package com.example.gabri.licentafrontend.Domain;

/**
 * Created by gabri on 5/2/2018.
 */

public class DetailsAboutReview {
    private String type;
    private double starsNumber;
    private String comment;
    private String user;
    private String date;

    public DetailsAboutReview() {
    }

    public DetailsAboutReview(String type, double starsNumber, String comment, String user, String date) {
        this.type = type;
        this.starsNumber = starsNumber;
        this.comment = comment;
        this.user = user;
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getStarsNumber() {
        return starsNumber;
    }

    public void setStarsNumber(double starsNumber) {
        this.starsNumber = starsNumber;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
