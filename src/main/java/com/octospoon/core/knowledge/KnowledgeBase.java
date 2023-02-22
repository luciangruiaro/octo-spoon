package com.octospoon.core.knowledge;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class KnowledgeBase {

    public static final String HELLO_MESSAGE = "Hello human! How are you?";
    public static final String DEFAULT_ANSWER = "Sorry, I did not understand. Could you please rephrase?";
    @Autowired
    SolrAPI solrAPI;

    public String getDataFromSolr(String inputStr) throws SolrServerException, IOException {
        return solrAPI.solrSearch(inputStr);
    }
}
