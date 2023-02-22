package com.octospoon.rest;

import com.octospoon.core.knowledge.Wikipedia;
import com.octospoon.core.nlp.TextAnalyzer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KnowledgeReq {

    @Autowired
    Wikipedia wikipedia;

    @Autowired
    TextAnalyzer textAnalyzer;


    @GetMapping("/wikipedia/{inputWord}")
    public String wiki(@PathVariable("inputWord") String inputWord) throws Exception {
        return wikipedia.wikipediaGetContent(inputWord);
    }

    @GetMapping("/relevantSentence")
    public String relevantSentence() throws Exception {
        String inputText = "France (French: Listen), officially the French Republic (French: République française ),[14] is a country located primarily in Western Europe. It also includes overseas regions and territories in the Americas and the Atlantic, Pacific and Indian Oceans,[XII] giving it one of the largest discontiguous exclusive economic zones in the world. Its metropolitan area extends from the Rhine to the Atlantic Ocean and from the Mediterranean Sea to the English Channel and the North Sea; overseas territories include French Guiana in South America, Saint Pierre and Miquelon in the North Atlantic, the French West Indies, and many islands in Oceania and the Indian Ocean. Its eighteen integral regions (five of which are overseas) span a combined area of 643,801 km2 (248,573 sq mi) and had a total population of over 68 million as of January 2023.France is a unitary semi-presidential republic with its capital in Paris, the country's largest city and main cultural and commercial centre; other major urban areas include Marseille, Lyon, Toulouse, Lille, Bordeaux, and Nice.";
        return textAnalyzer.extractRelevantSentences(inputText, "France", 1).get(0);
    }

    @GetMapping("/relevantSentenceWiki/{inputString}")
    public String relevantSentence(@PathVariable("inputString") String inputString) throws Exception {
        return textAnalyzer.extractRelevantSentences(
                wikipedia.wikipediaGetContent(inputString)
                , "France", 1).get(0);
    }
}
