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
    TelegramDelivery telegramDelivery;

    @Autowired
    TelegramInbox telegramInbox;

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


}
