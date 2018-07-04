package com.example.gabri.licentafrontend.Domain;

import android.util.Log;

/**
 * Created by gabri on 3/26/2018.
 */

public class User {
    private Long id;
    private String lastName;
    private String firstName;
    private String emailAddress;
    private String password;
    private String phoneNumber;
    private int status;

    public User() {
    }

    public User(String lastName, String firstName, String emailAddress, String password, String phoneNumber) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.emailAddress = emailAddress;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public User(Long id, String lastName, String firstName, String emailAddress, String password, String phoneNumber, int status) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.emailAddress = emailAddress;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.status = status;
        Log.d("status in constructor", status+"");
    }

    public User(Long id, String lastName, String firstName, String emailAddress, String password, String phoneNumber ) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.emailAddress = emailAddress;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int isStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
