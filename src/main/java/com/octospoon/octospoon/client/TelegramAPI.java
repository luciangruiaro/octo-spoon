package com.octospoon.octospoon.client;

import com.octospoon.octospoon.helper.GetReqCall;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TelegramAPI {

    private TelegramBot bot = new TelegramBot(TelegramConfig.TOKEN);

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
        GetUpdates getUpdates = new GetUpdates().limit(100).offset(0).timeout(0);

        GetUpdatesResponse updatesResponse = bot.execute(getUpdates);
        List<Update> updates = updatesResponse.updates();
        for (Update update : updates) {
            System.out.println(update.message());
        }
    }

    public boolean areThereUodates() {
        return true;
    }

}
