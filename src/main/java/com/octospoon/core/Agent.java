package com.octospoon.core;

import com.octospoon.client.TelegramDelivery;
import com.octospoon.config.TelegramConfig;
import com.octospoon.contacts.Conversation;
import com.octospoon.core.knowledge.KnowledgeBase;
import com.octospoon.core.lifecycle.DecisionEngine;
import com.octospoon.core.nlp.SentimentAnalyzer;
import com.octospoon.helper.MessageParser;
import com.octospoon.helper.TextUtils;
import com.octospoon.openAI.Completion;
import com.octospoon.persistent.RDS;
import com.pengrad.telegrambot.model.Message;
import org.springframework.ai.client.AiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

@EnableScheduling
@Component
public class Agent {

    @Autowired
    TelegramDelivery telegramDelivery;

    @Autowired
    DecisionEngine decisionEngine;

    @Autowired
    SentimentAnalyzer sentimentAnalyzer;
    @Autowired
    MessageParser messageParser;

    @Autowired
    KnowledgeBase knowledgeBase;

    @Autowired
    TextUtils textUtils;


    @Autowired
    RDS rds;

    String answer;
    private final AiClient aiClient;

    @Autowired
    public Agent(AiClient aiClient) {
        this.aiClient = aiClient;
    }


    public void generateReply(Message message) throws Exception {

        String conversationState = decisionEngine.decideCurrentCoversationState(message.text()).getName();

        Conversation conversation =
                new Conversation(String.valueOf(message.chat().id()), message.chat().username(), conversationState);

        // go to branch
        switch (conversationState) {
            case "Greetings" -> answer = KnowledgeBase.HELLO_MESSAGE;
            case "Question" -> {
                answer = knowledgeBase.getDataFromSolr(textUtils.extractWordsAfterAbout(message.text()));
                if (answer.isEmpty()) {
                    answer = KnowledgeBase.DEFAULT_IDK;
                    telegramDelivery.sendMessage(conversation, answer);
                    answer = knowledgeBase.searchForMoreKnowledge(message.text());
                }
            }
            case "ai" -> answer = new Completion(aiClient.generate(message.text())).getCompletion();
            default -> answer = KnowledgeBase.DEFAULT_ANSWER;
        }

        // print to console
        messageParser.printMessage(TelegramConfig.MESSAGE_RECEIVED, message);
        // store in RDS
        rds.saveMessage(conversation, answer, TelegramConfig.MESSAGE_RECEIVED);
        // send the message
        telegramDelivery.sendMessage(conversation, answer);
        telegramDelivery.sendMessage(conversation, decisionEngine.sentimentAnalyzerMessage(message.text()));
    }

}
