package com.octospoon.octospoon.helper;

import com.pengrad.telegrambot.model.Message;
import org.springframework.stereotype.Service;

@Service
public class MessageParser {

    public void printMessage(String messageType, Message message) {

        StringBuilder hrMessage = new StringBuilder();
        hrMessage.append(messageType);
        hrMessage.append(message.chat().id());
        hrMessage.append(", ");
        hrMessage.append(message.chat().username());
        hrMessage.append(" -> ");
        hrMessage.append(message.text());
        System.out.println(hrMessage);
    }

}
