package com.octospoon.octospoon.rest;


import com.octospoon.octospoon.client.TelegramAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestCtrl {

    @Autowired
    TelegramAPI telegramAPI;

    @GetMapping("/hi/{inputMessage}")
    public String hello(@PathVariable("inputMessage") String inputMessage) throws Exception {
        telegramAPI.senMessage("185790419", inputMessage);
        return "hi";
    }

    @GetMapping("/getUpdates")
    public String getUpdates() throws Exception {
        System.out.println("ddd");
        telegramAPI.getUpdates();
        return "getUpdates";
    }


}
