package com.octospoon.core;

import com.octospoon.client.TelegramConfig;
import com.octospoon.client.TelegramDelivery;
import com.octospoon.contacts.Conversation;
import com.octospoon.contacts.ConversationLine;
import com.octospoon.core.knowledge.KnowledgeBase;
import com.octospoon.core.knowledge.SolrAPI;
import com.octospoon.core.knowledge.Wikipedia;
import com.octospoon.core.lifecycle.DecisionEngine;
import com.octospoon.core.nlp.SentimentAnalyzer;
import com.octospoon.core.nlp.TextAnalyzer;
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
    Wikipedia wikipedia;

    @Autowired
    SolrAPI solrAPI;

    @Autowired
    TextAnalyzer textAnalyzer;

    @Autowired
    RDS rds;


    public void generateReply(Message message) throws Exception {

        String answer = "";
        String conversationState = decisionEngine.decideCurrentCoversationState(message.text()).getName();
        switch (conversationState) {
            case "Greetings" -> answer = KnowledgeBase.HELLO_MESSAGE;
            case "Question" -> {
                answer = knowledgeBase.getDataFromSolr(textUtils.extractWordsAfterAbout(message.text()));
                if (answer.isEmpty()) {
                    answer = "At this time, I do not possess the necessary knowledge to provide a conclusive response. However, I am committed to expanding my knowledge and will return with a more informed answer at a later time. Thank you for your understanding.";
                    telegramDelivery.sendMessage(
                            new Conversation(String.valueOf(message.chat().id()), message.chat().username()),
                            answer);
                    String keyWord = textUtils.extractWordsAfterAbout(message.text());
                    answer = textAnalyzer.extractRelevantSentences(
                            wikipedia.wikipediaGetContent(keyWord), keyWord, 1).get(0);
                    solrAPI.solrIndexer(answer, keyWord);
                }
            }
            default -> answer = KnowledgeBase.DEFAULT_ANSWER;
        }

        // print to console
        messageParser.printMessage(TelegramConfig.MESSAGE_RECEIVED, message);
        // store in RDS
        rds.saveConversation(new ConversationLine(null, String.valueOf(message.chat().id()), message.chat().username(),
                conversationState, message.text()));
        // send the message
        telegramDelivery.sendMessage(new Conversation(String.valueOf(message.chat().id()), message.chat().username()),
                answer);
    }

}
