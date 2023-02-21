package com.octospoon.octospoon.core.knowledge;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class Wikipedia {
    private static final String WIKIPEDIA_SEARCH_URL = "https://en.wikipedia.org/wiki/";
    @Autowired
    SolrAPI solrAPI;

    public String wikipediaGetContent(String inputString) throws Exception {

        String wikiPageUrl = WIKIPEDIA_SEARCH_URL + inputString.replace(" ", "_");
        String wikiPageContent = "";

        try {
            // Fetch the Wikipedia page for the search query
            Document doc = Jsoup.connect(wikiPageUrl).get();

            // Extract the content of the page
            Element content = doc.select("div#mw-content-text").first();
            wikiPageContent = content.text();

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(wikiPageContent);

        return wikiPageContent;
    }
}
