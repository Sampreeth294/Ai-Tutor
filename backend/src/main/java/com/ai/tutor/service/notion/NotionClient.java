package com.ai.tutor.service.notion;

import com.ai.tutor.config.NotionConfig.NotionProperties;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotionClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotionClient.class);
    private static final String NOTION_BASE_URL = "https://api.notion.com/v1";
    private static final String NOTION_VERSION = "2022-06-28";

    private final NotionProperties notionProperties;
    private final RestTemplate restTemplate;

    public NotionClient(NotionProperties notionProperties, RestTemplateBuilder restTemplateBuilder) {
        this.notionProperties = notionProperties;
        this.restTemplate = restTemplateBuilder
            .rootUri(NOTION_BASE_URL)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();

        if (notionProperties.hasToken()) {
            LOGGER.info("NotionClient initialized with token (value hidden).");
        } else {
            LOGGER.info("NotionClient initialized without token. All requests will use placeholder responses.");
        }
        LOGGER.debug("Notion RestTemplate initialized: {}", this.restTemplate.getClass().getSimpleName());
    }

    public String queryPage(String pageId) {
        if (!notionProperties.hasToken()) {
            LOGGER.info("Attempted Notion query without token; returning placeholder response.");
            return "notion-client-not-ready";
        }

        HttpHeaders headers = new HttpHeaders();
        String token = Objects.requireNonNull(notionProperties.token(), "Notion token must not be null when hasToken() is true");
        headers.setBearerAuth(token);
        headers.set("Notion-Version", NOTION_VERSION);

        LOGGER.debug("Prepared Notion request headers for page: {}", pageId);

        // Placeholder for future call
        return "notion-client-placeholder-response";
    }

    public boolean hasToken() {
        return notionProperties.hasToken();
    }
}

