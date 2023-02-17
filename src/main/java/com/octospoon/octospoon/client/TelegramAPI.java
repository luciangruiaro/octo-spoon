package com.octospoon.octospoon.client;

import com.octospoon.octospoon.helper.GetReqCall;
import com.octospoon.octospoon.helper.MessageParser;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class TelegramAPI {

    private final TelegramBot bot = new TelegramBot(TelegramConfig.TOKEN);
    @Autowired
    MessageParser messageParser;
    private Integer lastUpdateId = 0;

    public void senMessage(String chatId, String message) throws Exception {
        JSONObject bodyParams = new JSONObject();
        bodyParams.put(TelegramConfig.CHAT_ID, String.valueOf(chatId));
        bodyParams.put(TelegramConfig.TEXT, message);
        // send a string message to Telegram client
        GetReqCall.restCall(buildTelegramMethod(TelegramConfig.METHOD_SEND_MESSAGE), bodyParams);
    }

    private String buildTelegramMethod(String method) {
        StringBuilder result = new StringBuilder();
        result.append(TelegramConfig.BASE_URL);
        result.append("/");
        result.append(TelegramConfig.BOT_PREFIX);
        result.append(TelegramConfig.TOKEN);
        result.append("/");
        result.append(method);
        return result.toString();
    }

    public void getUpdates() {
//        GetUpdates getUpdates = new GetUpdates().limit(100).offset(0).timeout(0);
//        GetUpdatesResponse updatesResponse = bot.execute(getUpdates);

        bot.setUpdatesListener(new UpdatesListener() {
            @Override
            public int process(List<Update> updates) {
                for (Update update : updates) {
                    messageParser.printMessage(update);
                }
                return UpdatesListener.CONFIRMED_UPDATES_ALL;
            }
        });

    }


}
