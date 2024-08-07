package org.example.digitalculturalportal.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

import java.time.Duration;

@Configuration
public class RestClientConfig extends AbstractElasticsearchConfiguration {

    @Bean
    @Override
    public RestHighLevelClient elasticsearchClient() {
        // 使用构建器来提供集群地址，设置默认值HttpHeaders或启用SSL
        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                // 设置连接地址
                .connectedTo("127.0.0.1:9200")
                .withConnectTimeout(Duration.ofSeconds(10))
                .withSocketTimeout(Duration.ofSeconds(30))
                // 创建连接信息
                .build();
        // 创建RestHighLevelClient
        return RestClients.create(clientConfiguration).rest();
    }

}
