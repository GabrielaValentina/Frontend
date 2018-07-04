package com.example.gabri.licentafrontend.Domain;

/**
 * Created by gabri on 5/25/2018.
 */

public class UsersListChatAdapter {
    private Long id_user;
    private String name;
    private String current_train;
    private Boolean isOnline;

    public UsersListChatAdapter() {
    }

    public UsersListChatAdapter( String name, String current_train, Boolean isOnline) {
        this.name = name;
        this.current_train = current_train;
        this.isOnline = isOnline;
    }

    public UsersListChatAdapter(Long id_user, String name, String current_train, Boolean isOnline) {
        this.id_user = id_user;
        this.name = name;
        this.current_train = current_train;
        this.isOnline = isOnline;
    }

    public Long getId_user() {
        return id_user;
    }

    public void setId_user(Long id_user) {
        this.id_user = id_user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrent_train() {
        return current_train;
    }

    public void setCurrent_train(String current_train) {
        this.current_train = current_train;
    }

    public Boolean getOnline() {
        return isOnline;
    }

    public void setOnline(Boolean online) {
        isOnline = online;
    }

    @Override
    public String toString() {
        return "UsersListChatAdapter{" +
                "id_user=" + id_user +
                ", name='" + name + '\'' +
                ", current_train='" + current_train + '\'' +
                ", isOnline=" + isOnline +
                '}';
    }
}
