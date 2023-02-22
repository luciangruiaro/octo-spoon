package com.octospoon.rest;


import com.octospoon.client.TelegramDelivery;
import com.octospoon.contacts.Conversation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TelegramReq {

    @Autowired
    TelegramDelivery telegramDelivery;

    @GetMapping("/hi/{inputMessage}")
    public String hello(@PathVariable("inputMessage") String inputMessage)
            throws Exception {
        telegramDelivery.sendMessage(new Conversation("185790419", ""), inputMessage);
        return "hi";
    }


}
