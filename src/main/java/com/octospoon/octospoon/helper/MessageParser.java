package com.octospoon.octospoon.helper;

import com.pengrad.telegrambot.model.Message;
import org.springframework.stereotype.Service;

@Service
public class MessageParser {

    public void printMessage(String messageType, Message message) {
        StringBuilder humanReadableMessage = new StringBuilder();
        humanReadableMessage.append(messageType);
        humanReadableMessage.append("\t");
        humanReadableMessage.append(message.chat().id());
        humanReadableMessage.append(",");
        humanReadableMessage.append(message.chat().username());
        humanReadableMessage.append(" -> ");
        humanReadableMessage.append(message.text());

        System.out.println(humanReadableMessage);
    }
}
