package com.octospoon.contacts;


public class Conversation {


    public String chatId;

    public String username;
    public String conversationState;

    public Conversation(String chatId, String username, String conversationState) {
        this.chatId = chatId;
        this.username = username;
        this.conversationState = conversationState;
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

    public String getConversationState() {
        return conversationState;
    }

    public void setConversationState(String conversationState) {
        this.conversationState = conversationState;
    }


}
