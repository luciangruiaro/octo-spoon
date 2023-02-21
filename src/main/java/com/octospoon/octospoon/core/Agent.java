package com.octospoon.octospoon.core;

import com.octospoon.octospoon.client.TelegramConfig;
import com.octospoon.octospoon.client.TelegramDelivery;
import com.octospoon.octospoon.contacts.Conversation;
import com.octospoon.octospoon.core.knowledge.KnowledgeBase;
import com.octospoon.octospoon.core.lifecycle.DecisionMaker;
import com.octospoon.octospoon.helper.MessageParser;
import com.pengrad.telegrambot.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@EnableScheduling
@Component
public class Agent {

    @Autowired
    TelegramDelivery telegramDelivery;

    @Autowired
    DecisionMaker decisionMaker;
    @Autowired
    MessageParser messageParser;

    public void provideAnswer(Message message) {

        // register conversation
        List<Conversation> conversations = new ArrayList<>();
        Conversation conversation = new Conversation();
        conversation.setChatId(String.valueOf(message.chat().id()));
        conversation.setUsername(String.valueOf(message.chat().username()));
        conversations.add(conversation);


        // decide what to answer
        String answer = KnowledgeBase.DEFAULT_ANSWER;
        if (decisionMaker.isInitialMessage(message.text())) {
            answer = KnowledgeBase.HELLO_MESSAGE;
        }
        answer = answer + "\t" + "Your mood is: " + decisionMaker.sentimentAnalyzer(message.text());


        // send the message
        messageParser.printMessage(TelegramConfig.MESSAGE_RECEIVED, message);
        telegramDelivery.sendMessage(conversation, answer);
    }


}
