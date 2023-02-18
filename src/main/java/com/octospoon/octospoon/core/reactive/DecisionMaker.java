package com.octospoon.octospoon.core.reactive;

import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DecisionMaker {


    public boolean isInitialMessage(String text) {
        return text.contains("hi") || text.contains("hello");
    }

    public String sentimentAnalyzer(String text) {
        StanfordCoreNLP stanfordCoreNLP = Pipeline.getPipeline();
        String retSentiment = "";

        CoreDocument coreDocument = new CoreDocument(text);
        stanfordCoreNLP.annotate(coreDocument);
        List<CoreSentence> sentences = coreDocument.sentences();

        for (CoreSentence sentence : sentences) {
            String sentiment = sentence.sentiment();
            retSentiment = retSentiment + " " + sentiment;
        }
        return retSentiment;
    }

}
