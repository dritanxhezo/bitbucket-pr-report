package com.github.ngeor.web2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ngeor.bitbucket.BitbucketClient;
import com.github.ngeor.http.HttpClientImpl;
import com.github.ngeor.http.JsonHttpClientImpl;

/**
 * Configuration for the Bitbucket client.
 */
@Configuration
public class BitbucketConfiguration {
    @Value("${bitbucket.username}")
    private String username;

    @Value("${bitbucket.password}")
    private String password;

    @Value("${bitbucket.owner}")
    private String owner;

    /**
     * Gets the Bitbucket client.
     */
    @Bean
    public BitbucketClient bitbucketClient(ObjectMapper objectMapper) {
        BitbucketClient result =
            new BitbucketClient(JsonHttpClientImpl.create(new HttpClientImpl(username, password), objectMapper));
        result.setOwner(owner);
        return result;
    }
}
