package com.example.gabri.licentafrontend.Domain;

/**
 * Created by gabri on 6/2/2018.
 */

public class MessageAdapter {
    private Long id;
    private Long id_sender;
    private String name;
    private String message;
    private String data;

    public MessageAdapter() {
    }

    public MessageAdapter(Long id, Long id_sender, String name, String message, String data) {
        this.id = id;
        this.id_sender = id_sender;
        this.name = name;
        this.message = message;
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId_sender() {
        return id_sender;
    }

    public void setId_sender(Long id_sender) {
        this.id_sender = id_sender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
