package com.ai.tutor.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotionConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotionConfig.class);

    @Bean
    public NotionProperties notionProperties(@Value("${notion.api.token:}") String notionToken) {
        if (notionToken == null || notionToken.isBlank()) {
            LOGGER.warn("Notion API token is not set. Notion features will be disabled.");
        } else {
            LOGGER.info("Notion API token detected (value hidden).");
        }
        return new NotionProperties(notionToken);
    }

    public record NotionProperties(String token) {
        public boolean hasToken() {
            return token != null && !token.isBlank();
        }
    }
}

