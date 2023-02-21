package com.octospoon.octospoon.core;

import com.octospoon.octospoon.client.TelegramConfig;
import com.octospoon.octospoon.client.TelegramDelivery;
import com.octospoon.octospoon.contacts.Conversation;
import com.octospoon.octospoon.core.knowledge.KnowledgeBase;
import com.octospoon.octospoon.core.lifecycle.DecisionMaker;
import com.octospoon.octospoon.helper.MessageParser;
import com.pengrad.telegrambot.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Agent {

    @Autowired
    DecisionMaker decisionMaker;

    @Autowired
    MessageParser messageParser;

    @Autowired
    TelegramDelivery telegramDelivery;

    public void provideAnswer(Message message) {

        // register conversation
        List<Conversation> conversationList = new ArrayList<>();
        Conversation conversation = new Conversation();
        conversation.setChatId(String.valueOf(message.chat().id()));
        conversation.setUsername(message.chat().username());
        conversationList.add(conversation);

        // decide what to answer
        String answer = KnowledgeBase.DEFAULT_MESSAGE;
        if (decisionMaker.isInitialMessage(message.text())) {
            answer = KnowledgeBase.HELLO_MESSAGE;
        }

        // send the message
        messageParser.printMessage(TelegramConfig.MESSAGE_TYPE_RECEIVED, message);
        telegramDelivery.sendMessage(conversation, answer);
    }
}
