package com.octospoon.octospoon.client;

import com.octospoon.octospoon.core.Agent;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TelegramInbox {

    @Autowired
    Agent agent;

    TelegramBot bot = new TelegramBot(TelegramConfig.TOKEN);


    public void getUnreadMessages() {
        bot.setUpdatesListener(new UpdatesListener() {
            @Override
            public int process(List<Update> updates) {
                for (Update update : updates) {
                    agent.provideAnswer(update.message());
                }
                return UpdatesListener.CONFIRMED_UPDATES_ALL;
            }
        });

    }
}
