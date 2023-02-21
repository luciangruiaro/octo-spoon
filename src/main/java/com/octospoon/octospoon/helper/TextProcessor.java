package com.octospoon.octospoon.helper;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TextProcessor {

        public static List<String> getPhrasesAroundWord(String text, String targetWord, int n) {
        List<String> phrases = new ArrayList<>();
        String[] sentences = text.split("\\.");

        for (String sentence : sentences) {
            if (sentence.toLowerCase().contains(targetWord.toLowerCase())) {
                String[] words = sentence.split(" ");
                int targetIndex = -1;

                // Find the index of the target word in the current sentence
                for (int i = 0; i < words.length; i++) {
                    if (words[i].equalsIgnoreCase(targetWord)) {
                        targetIndex = i;
                        break;
                    }
                }

                // Add n phrases before and after the target word to the list
                int start = Math.max(0, targetIndex - n);
                int end = Math.min(words.length - 1, targetIndex + n);

                StringBuilder sb = new StringBuilder();
                for (int i = start; i <= end; i++) {
                    sb.append(words[i]).append(" ");
                }

                phrases.add(sb.toString().trim());
            }
        }

        return phrases;
    }

}
