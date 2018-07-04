package com.example.gabri.licentafrontend.Domain.Mappers;

import java.sql.Timestamp;

/**
 * Created by gabri on 5/25/2018.
 */

public class ChatMessageMapper {
    private int id_sender;
    private String name;
    private String message;
    private String sentAt;

    public ChatMessageMapper() {
    }

    public ChatMessageMapper(int id_sender, String name, String message, String sentAt) {
        this.id_sender = id_sender;
        this.name = name;
        this.message = message;
        this.sentAt = sentAt;
    }

    public int getId_sender() {
        return id_sender;
    }

    public void setId_sender(int id_sender) {
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

    public String getSentAt() {
        return sentAt;
    }

    public void setSentAt(String sentAt) {
        this.sentAt = sentAt;
    }
}
