package com.octospoon.octospoon.core.knowledge;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class WikipediaDownloader {
    private static final String WIKIPEDIA_SEARCH_URL = "https://en.wikipedia.org/w/index.php?search=%s&title=Special:Search&go=Go&ns0=1";
    @Autowired
    SolrAPI solrAPI;

    public String wikiDownloader(String inputWord) throws Exception {

        String searchUrl = String.format(WIKIPEDIA_SEARCH_URL, URLEncoder.encode(inputWord, StandardCharsets.UTF_8));

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(searchUrl);
//        httpGet.addHeader("User-Agent", "Mozilla/5.0");
        String htmlContent;
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(httpClient.execute(httpGet).getEntity().getContent()))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            htmlContent = sb.toString();
        }

        if (!htmlContent.isEmpty()) {
            solrAPI.solrIndexer(htmlContent);
        }
        return htmlContent;
    }
}
