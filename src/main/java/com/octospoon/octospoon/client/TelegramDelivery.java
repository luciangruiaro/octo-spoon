package com.octospoon.octospoon.client;

import com.octospoon.octospoon.contacts.Conversation;
import com.octospoon.octospoon.helper.MessageParser;
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

    public void sendMessage(Conversation conversation, String text) {

        SendMessage sendRequest = new SendMessage(conversation.getChatId(), text);
        SendResponse sendResponse = bot.execute(sendRequest);

        if (sendResponse.isOk()) {
            messageParser.printMessage(TelegramConfig.MESSAGE_TYPE_SENT,
                    sendResponse.message());
        } else {
            System.out.println("Could not send message to Telegram");
        }


    }

}
