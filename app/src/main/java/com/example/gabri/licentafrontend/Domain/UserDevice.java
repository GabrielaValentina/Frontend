package com.example.gabri.licentafrontend.Domain;

/**
 * Created by gabri on 5/26/2018.
 */

public class UserDevice {

    private Long id_user;
    private String id_device;
    private boolean isOnline;

    public UserDevice(Long id_user, String id_device, boolean isOnline) {
        this.id_user = id_user;
        this.id_device = id_device;
        this.isOnline = isOnline;
    }

    public Long getId_user() {
        return id_user;
    }

    public void setId_user(Long id_user) {
        this.id_user = id_user;
    }

    public String getId_device() {
        return id_device;
    }

    public void setId_device(String id_device) {
        this.id_device = id_device;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    @Override
    public String toString() {
        return "UserDevice{" +
                "id_user=" + id_user +
                ", id_device='" + id_device + '\'' +
                ", isOnline=" + isOnline +
                '}';
    }
}
