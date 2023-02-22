package com.octospoon.helper;

import com.pengrad.telegrambot.model.Message;
import org.springframework.stereotype.Service;

@Service
public class MessageParser {

    public void printMessage(String messageType, Message message) {
        String hrMessage = messageType +
                message.chat().id() +
                ", " +
                message.chat().username() +
                " -> " +
                message.text();
        System.out.println(hrMessage);
    }
}
