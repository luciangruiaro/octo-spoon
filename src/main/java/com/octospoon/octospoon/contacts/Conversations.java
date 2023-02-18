package com.octospoon.octospoon.contacts;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class Conversations {
    List<Conversation> conversations;

    public void registerConversation(Conversation conv) {
        this.conversations.add(conv);
    }

    public List<Conversation> getConversations() {
        return conversations;
    }

    public void setConversations(List<Conversation> conversations) {
        this.conversations = conversations;
    }
}
