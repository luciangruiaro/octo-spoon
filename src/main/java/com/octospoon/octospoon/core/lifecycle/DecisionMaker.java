package com.octospoon.octospoon.core.lifecycle;

import org.springframework.stereotype.Service;

@Service
public class DecisionMaker {

    public boolean isInitialMessage(String message) {
        return message.contains("hi") || message.contains("hello") || message.contains("ping");
    }
}
