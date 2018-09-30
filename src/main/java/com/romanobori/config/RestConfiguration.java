package com.romanobori.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfiguration {


    private final RestTemplate restTemplate = new RestTemplate();


    public RestConfiguration() {
        restTemplate.setErrorHandler(new EmptyResponseErrorHandler());
    }

    @Bean
    public RestTemplate restTemplate() {
        return restTemplate;
    }

    public static class EmptyResponseErrorHandler implements ResponseErrorHandler {
        @Override
        public void handleError(ClientHttpResponse response) {
        }
        @Override
        public boolean hasError(ClientHttpResponse response) {
            return false;
        }
    }
}
