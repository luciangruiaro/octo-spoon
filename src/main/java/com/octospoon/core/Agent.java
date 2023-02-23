package com.octospoon.core;

import com.octospoon.client.TelegramDelivery;
import com.octospoon.config.TelegramConfig;
import com.octospoon.contacts.Conversation;
import com.octospoon.core.knowledge.KnowledgeBase;
import com.octospoon.core.lifecycle.DecisionEngine;
import com.octospoon.helper.MessageParser;
import com.octospoon.helper.TextUtils;
import com.octospoon.persistent.RDS;
import com.pengrad.telegrambot.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

@EnableScheduling
@Component
public class Agent {

    @Autowired
    DecisionEngine decisionEngine;

    @Autowired
    KnowledgeBase knowledgeBase;

    @Autowired
    MessageParser messageParser;

    @Autowired
    TelegramDelivery telegramDelivery;

    @Autowired
    TextUtils textUtils;

    @Autowired
    RDS rds;

    String answer;

    public void generateReply(Message message) throws Exception {
        String conversationState = decisionEngine.decideCurrentConversationState(
                message.text()).getName();

        Conversation conversation =
                new Conversation(
                        String.valueOf(message.chat().id()),
                        message.chat().username(),
                        conversationState
                );

        switch (conversationState) {
            case "Greetings" -> {
                answer = KnowledgeBase.HELLO_MESSAGE;
            }
            case "Question" -> {
                answer = knowledgeBase.getdataFromSolr(
                        textUtils.extractWordsAfterAbout(message.text())
                );
                if (answer.isEmpty()) {
                    answer = KnowledgeBase.DEFAULT_IDK;
                    telegramDelivery.sendMessage(conversation, answer);
                    answer = knowledgeBase.searchForMoreKnowledge(message.text());
                }
            }
            default -> answer = KnowledgeBase.DEFAULT_ANSWER;
        }

        // print to console
        messageParser.printMessage(TelegramConfig.MESSAGE_RECEIVED, message);

        // save to database
        rds.saveMessage(conversation, answer, TelegramConfig.MESSAGE_RECEIVED);

        // deliver the message
        telegramDelivery.sendMessage(conversation,
                answer);
        telegramDelivery.sendMessage(conversation,
                decisionEngine.sentimentAnalyzerMessage(message.text())
        );


    }

}
