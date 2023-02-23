package com.octospoon.client;


import com.octospoon.config.TelegramConfig;
import com.octospoon.contacts.Conversation;
import com.octospoon.helper.MessageParser;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TelegramDelivery {


    @Autowired
    MessageParser messageParser;
    TelegramBot bot = new TelegramBot(TelegramConfig.TOKEN);

    // send message
    public void sendMessage(Conversation conversation, String rawMessage) {
        SendMessage sendRequest = new SendMessage(conversation.getChatId(), rawMessage);
        SendResponse sendResponse = bot.execute(sendRequest);
        if (sendResponse.isOk()) {
            messageParser.printMessage(TelegramConfig.MESSAGE_SENT,
                    sendResponse.message());
        } else {
            System.out.println("Could not send message at this moment.");
        }

    }


}
