package com.octospoon.octospoon.core.lifecycle;

import org.springframework.stereotype.Service;

@Service
public class DecisionEngine {


    public boolean isInitialMessage(String text) {
        return text.contains("hi") || text.contains("hello");
    }


}
