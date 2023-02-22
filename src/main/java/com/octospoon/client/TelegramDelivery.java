package com.octospoon.client;


import com.octospoon.config.TelegramConfig;
import com.octospoon.contacts.Conversation;
import com.octospoon.helper.MessageParser;
import com.octospoon.persistent.RDS;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TelegramDelivery {

    @Autowired
    MessageParser messageParser;
    @Autowired
    RDS rds;
    TelegramBot bot = new TelegramBot(TelegramConfig.TOKEN);
    List<Update> unreadMessages = new ArrayList<>();

    // send message
    public void sendMessage(Conversation conversation, String rawMessage) throws SQLException {
        SendMessage request = new SendMessage(conversation.getChatId(), rawMessage);
        SendResponse sendResponse = bot.execute(request);
        if (sendResponse.isOk()) {
            messageParser.printMessage(TelegramConfig.MESSAGE_SENT, sendResponse.message());
            rds.saveMessage(
                    conversation,
                    rawMessage,
                    TelegramConfig.MESSAGE_SENT
            );
        } else {
            System.out.println("Could not send the message at this time.");
        }
    }


}
