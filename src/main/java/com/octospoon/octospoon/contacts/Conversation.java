package com.octospoon.octospoon.contacts;

import org.springframework.stereotype.Repository;

@Repository
public class Conversation {
    private String chatId;
    private String username;

    public Conversation(String chatId, String username) {
        this.chatId = chatId;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }
}
