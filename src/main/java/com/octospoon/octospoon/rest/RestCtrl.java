package com.octospoon.octospoon.rest;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestCtrl {

    @GetMapping("/hi")
    public String hello() {
        return "hi";
    }


}
