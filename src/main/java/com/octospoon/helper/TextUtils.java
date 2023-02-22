package com.octospoon.helper;

import org.springframework.stereotype.Service;

@Service
public class TextUtils {


    public boolean containsAnyWord(String text, String[] words) {
        String[] tokens = text.split("\\s+"); // split text into words
        for (String token : tokens) {
            for (String word : words) {
                if (token.equalsIgnoreCase(word)) {
                    return true;
                }
            }
        }
        return false;
    }


    public String extractWordsAfterAbout(String sentence) {
        String[] words = sentence.split(" ");
        boolean foundAbout = false;
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            if (foundAbout) {
                sb.append(word);
                sb.append(" ");
            } else if (word.equalsIgnoreCase("about")) {
                foundAbout = true;
            }
        }
        return sb.toString().trim();
    }

}
