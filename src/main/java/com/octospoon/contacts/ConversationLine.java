package com.octospoon.contacts;


import jakarta.persistence.*;

@Entity
@Table(name = "conversation_lines")
public class ConversationLine {

    @Id
    @Column(name = "lineId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lineId;
    @Column(name = "chatId")

    private String chat_id;
    @Column(name = "userName")

    private String userName;
    @Column(name = "conversationState")


    private String conversationState;
    @Column(name = "message")

    private String message;

    public ConversationLine(Long lineId, String chat_id, String userName, String conversationState, String message) {
        this.lineId = lineId;
        this.chat_id = chat_id;
        this.userName = userName;
        this.conversationState = conversationState;
        this.message = message;
    }

    public Long getLineId() {
        return lineId;
    }

    public void setLineId(Long lineId) {
        this.lineId = lineId;
    }

    public String getChat_id() {
        return chat_id;
    }

    public void setChat_id(String chat_id) {
        this.chat_id = chat_id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getConversationState() {
        return conversationState;
    }

    public void setConversationState(String conversationState) {
        this.conversationState = conversationState;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
