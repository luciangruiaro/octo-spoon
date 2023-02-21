package com.octospoon.octospoon.rest;


import com.octospoon.octospoon.client.TelegramDelivery;
import com.octospoon.octospoon.client.TelegramInbox;
import com.octospoon.octospoon.contacts.Conversation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestCtrl {

    @Autowired
    TelegramDelivery deliveryEngine;

    @Autowired
    TelegramInbox inbox;

    @GetMapping("/hi")
    public String hello() {
        return "hi";
    }

    @GetMapping("/sendMessage/{ourInputMessage}")
    public String interfaceSendMessage(@PathVariable("ourInputMessage") String ourInputMessage) {
        Conversation conversation = new Conversation();
        conversation.setChatId("185790419");
        deliveryEngine.sendMessage(conversation, ourInputMessage);
        return "Success!";
    }

    @GetMapping("/processUnreadMessages")
    public String processMessages() {
        inbox.getUnreadMessages();
        return "Success!";
    }


}
