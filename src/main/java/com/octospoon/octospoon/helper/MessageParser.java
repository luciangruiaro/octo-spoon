package com.octospoon.octospoon.helper;

import com.pengrad.telegrambot.model.Update;
import org.springframework.stereotype.Service;

@Service
public class MessageParser {

    public void printMessage(Update update) {

        StringBuilder hrMessage = new StringBuilder();
//        hrMessage.append("updateId: ");
//        hrMessage.append(update.updateId());
//        hrMessage.append(" | ");
//        hrMessage.append("username: ");
        hrMessage.append(update.message().chat().username());
        hrMessage.append(" -> ");
//        hrMessage.append("message_id: ");
//        hrMessage.append(update.message().messageId());
//        hrMessage.append(" | ");
//        hrMessage.append("text: ");
        hrMessage.append(update.message().text());

        System.out.println(hrMessage);
    }

}
