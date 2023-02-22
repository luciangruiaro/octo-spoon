package com.octospoon.rest;

import com.octospoon.core.knowledge.SolrAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SolrReq {

    @Autowired
    SolrAPI solrAPI;

    @GetMapping("/solrIndexer/{inputWord}")
    public String solrIndexer(@PathVariable("inputWord") String inputWord) throws Exception {
        solrAPI.solrIndexer(inputWord, inputWord);
        return "let's see";
    }

    @GetMapping("/solrSearch/{inputWord}")
    public String solrSearch(@PathVariable("inputWord") String inputWord) throws Exception {
        return solrAPI.solrSearch(inputWord);
    }
}
