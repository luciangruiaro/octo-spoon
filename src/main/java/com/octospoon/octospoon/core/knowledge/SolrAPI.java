package com.octospoon.octospoon.core.knowledge;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Service;

@Service
public class SolrAPI {
    public void solrIndexer(String str) throws Exception {
        SolrClient solr = new HttpSolrClient.Builder(SolrConfig.SOLR_BASE_URL + SolrConfig.SOLR_CORE).build();

        SolrInputDocument doc = new SolrInputDocument();
        doc.addField(SolrConfig.SOLR_CONTENT_FIELD, str);

        solr.add(doc);
        solr.commit();
        solr.close();
    }
}
