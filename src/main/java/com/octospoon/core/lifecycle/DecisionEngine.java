package com.octospoon.core.lifecycle;

import com.octospoon.core.nlp.SentimentAnalyzer;
import com.octospoon.helper.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class DecisionEngine {

    @Autowired
    Flow flow;

    @Autowired
    TextUtils textUtils;

    @Autowired
    SentimentAnalyzer sentimentAnalyzer;

    public boolean isInitialMessage(String text) {
        return decideCurrentCoversationState(text).getPriority() == 0;
    }

    public State decideCurrentCoversationState(String text) {
        List<State> candidatesState = new ArrayList<>();
        for (State state : flow.setupFlow()) {
            if (textUtils.containsAnyWord(text, state.getKeywords())) {
                candidatesState.add(state);
            }
        }
        if (!candidatesState.isEmpty()) {
            return candidatesState.stream().min(Comparator.comparing(State::getPriority)).orElse(null);
        }
        return flow.getDefaultState();
    }

    public String sentimentAnalyzerMessage(String inputText) {
        return "By the way, you mood is now: " + sentimentAnalyzer.sentimentAnalyzer(inputText);
    }


}
