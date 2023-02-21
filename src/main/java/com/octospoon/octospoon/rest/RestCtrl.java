package com.octospoon.octospoon.rest;


import com.octospoon.octospoon.client.TelegramDelivery;
import com.octospoon.octospoon.client.TelegramInbox;
import com.octospoon.octospoon.contacts.Conversation;
import com.octospoon.octospoon.core.knowledge.SolrAPI;
import com.octospoon.octospoon.core.knowledge.WikipediaDownloader;
import com.octospoon.octospoon.helper.TextProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RestCtrl {

    @Autowired
    TelegramDelivery telegramDelivery;
    @Autowired
    SolrAPI solrAPI;

    @Autowired
    WikipediaDownloader wikipediaDownloader;

    @Autowired
    TelegramInbox telegramInbox;

    @Autowired
    TextProcessor textProcessor;

    @GetMapping("/hi/{inputMessage}")
    public String hello(@PathVariable("inputMessage") String inputMessage)
            throws Exception {
        Conversation conversation = new Conversation();
        conversation.setChatId("185790419");
        conversation.setUsername(null);
        telegramDelivery.sendMessage(conversation, inputMessage);
        return "hi";
    }

    @GetMapping("/checkInbox")
    public String checkInbox() {
        telegramInbox.getUnreadMessages();
        return "check inbox done";
    }

    @GetMapping("/solrIndexer/{inputWord}")
    public String solrIndexer(@PathVariable("inputWord") String inputWord) throws Exception {
        solrAPI.solrIndexer(inputWord);
        return "let's see";
    }

    @GetMapping("/wikiDownloader/{inputWord}")
    public String wikiDownloader(@PathVariable("inputWord") String inputWord) throws Exception {
        String res = "";
        List<String> results = new ArrayList<>();
        results = textProcessor.getPhrasesAroundWord(wikipediaDownloader.wikiDownloader(inputWord), inputWord, 3);
        for (String str : results) {
            res = res + str + "\n\n";
        }
        return res;
    }


}
