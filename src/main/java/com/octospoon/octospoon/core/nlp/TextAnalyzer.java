package com.octospoon.octospoon.core.nlp;

import org.springframework.stereotype.Service;

import java.text.BreakIterator;
import java.util.*;
import java.util.stream.IntStream;

@Service
public class TextAnalyzer {


    public List<String> extractRelevantSentences(String text, String word, int noSentences) {

        List<String> sentences = tokenizeTextInSentences(text);
        List<Map<String, Double>> tfIdfList = extractRelevantPhrases(sentences, word);

        // Rank sentences by their TF-IDF scores
        List<Integer> sentenceIndices = IntStream.range(0, sentences.size() - 1)
                .boxed()
                .sorted((i, j) -> Double.compare(getScore(tfIdfList.get(j)), getScore(tfIdfList.get(i))))
                .limit(noSentences)
                .toList();

        // Extract the top n sentences
        List<String> result = new ArrayList<>();
        for (int i : sentenceIndices) {
            result.add(sentences.get(i));
        }
        return result;
    }

    private List<String> tokenizeTextInSentences(String text) {
        // Tokenize the text into sentences
        BreakIterator sentenceIterator = BreakIterator.getSentenceInstance(Locale.US);
        sentenceIterator.setText(text);
        List<String> sentences = new ArrayList<>();
        int start = sentenceIterator.first();
        for (int end = sentenceIterator.next(); end != BreakIterator.DONE; start = end, end = sentenceIterator.next()) {
            sentences.add(text.substring(start, end));
        }
        return sentences;
    }

    private List<Map<String, Double>> extractRelevantPhrases(List<String> sentences, String word) {

        // Calculate TF-IDF scores for each sentence
        List<Map<String, Double>> tfIdfList = new ArrayList<>();
        for (String sentence : sentences) {
            Map<String, Double> tfMap = new HashMap<>();
            String[] words = sentence.split("\\s+");
            int wordCount = 0;
            for (String w : words) {
                if (w.matches("\\p{Punct}")) {
                    continue;
                }
                if (w.equalsIgnoreCase(word)) {
                    wordCount++;
                }
                tfMap.put(w, tfMap.getOrDefault(w, 0.0) + 1.0);
            }
            if (tfMap.isEmpty()) {
                continue; // or throw an exception, depending on what you want to do
            }
            double maxTf = Collections.max(tfMap.values());
            Map<String, Double> tfIdfMap = new HashMap<>();
            for (Map.Entry<String, Double> entry : tfMap.entrySet()) {
                String w = entry.getKey();
                double tf = entry.getValue() / maxTf;
                double idf = Math.log(sentences.size() / (double) countSentencesWithWord(sentences, w));
                tfIdfMap.put(w, tf * idf);
            }
            tfIdfList.add(tfIdfMap);
        }
        return tfIdfList;
    }

    private int countSentencesWithWord(List<String> sentences, String word) {
        int count = 0;
        for (String sentence : sentences) {
            if (sentence.contains(word)) {
                count++;
            }
        }
        return count;
    }

    private double getScore(Map<String, Double> tfIdfMap) {
        return tfIdfMap.values().stream()
                .mapToDouble(Double::doubleValue)
                .sum();
    }


}
