package com.octospoon.octospoon.core;

import com.octospoon.octospoon.client.TelegramAPI;
import com.octospoon.octospoon.client.TelegramConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Clock;

//@Configuration
@EnableScheduling
@Component
public class Agent {

    @Autowired
    TelegramAPI telegramAPI;

    @Scheduled(fixedDelay = 5000)
    public void repetitiveMessage() throws Exception {
        telegramAPI.senMessage(TelegramConfig.MY_CHAT_ID, "Hello, now the time is: " + Clock.systemUTC().instant());
    }
}
