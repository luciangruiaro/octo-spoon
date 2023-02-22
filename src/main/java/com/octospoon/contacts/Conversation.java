package com.octospoon.contacts;


import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class Conversation implements Serializable {


    public String chatId;

    public String username;

    public Conversation() {
    }

    public Conversation(String chatId, String username) {
        this.chatId = chatId;
        this.username = username;
    }


    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


}
