package com.example.gabri.licentafrontend.Domain;

/**
 * Created by gabri on 5/20/2018.
 */

public class UserLandmarkAppreciation {
    //the user id
    private Long id_user;
    //the landmark id
    private Long id_landmark;
    //its value can be 'like' or 'unlike'
    private String appreciation;

    public UserLandmarkAppreciation() {
    }

    public UserLandmarkAppreciation(Long id_user, Long id_landmark, String appreciation) {
        this.id_user = id_user;
        this.id_landmark = id_landmark;
        this.appreciation = appreciation;
    }

    public Long getId_user() {
        return id_user;
    }

    public void setId_user(Long id_user) {
        this.id_user = id_user;
    }

    public Long getId_landmark() {
        return id_landmark;
    }

    public void setId_landmark(Long id_landmark) {
        this.id_landmark = id_landmark;
    }

    public String getAppreciation() {
        return appreciation;
    }

    public void setAppreciation(String appreciation) {
        this.appreciation = appreciation;
    }

    @Override
    public String toString() {
        return "UserLandmark{" +
                "id_user=" + id_user +
                ", id_landmark=" + id_landmark +
                ", appreciation='" + appreciation + '\'' +
                '}';
    }
}
