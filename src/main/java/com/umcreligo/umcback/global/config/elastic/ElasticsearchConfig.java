package com.umcreligo.umcback.global.config.elastic;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

@Configuration
public class ElasticsearchConfig extends AbstractElasticsearchConfiguration {
    @Value("${elasticsearch.host}")
    private String host;
    @Value("${elasticsearch.user}")
    private String user;
    @Value("${elasticsearch.password}")
    private String password;

    @Override
    public RestHighLevelClient elasticsearchClient() {
        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
            .connectedTo(this.host)
            .withBasicAuth(this.user, this.password)
            .build();

        return RestClients.create(clientConfiguration).rest();
    }
}
