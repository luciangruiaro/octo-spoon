package com.octospoon.client;


import com.octospoon.config.TelegramConfig;
import com.octospoon.core.Agent;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import jakarta.annotation.PostConstruct;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class TelegramInbox {

    TelegramBot bot = new TelegramBot(TelegramConfig.TOKEN);

    @Autowired
    Agent agent;
    // check message inbox

    @PostConstruct
    public void getUnreadMessages() {
        bot.setUpdatesListener(new UpdatesListener() {
            @Override
            public int process(List<Update> list) {
                for (Update update : list) {
                    try {
                        agent.generateReply(update.message());
                    } catch (Exception e) {
                        System.out.println("warning");
                    }
                }
                return -1;
            }
        });
    }

}
