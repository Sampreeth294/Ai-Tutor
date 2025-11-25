package com.ai.tutor.service.notion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class NotionClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotionClient.class);

    private final WebClient webClient;
    private final String notionToken;
    private final String notionVersion;
    private final String parentPageId;

    public NotionClient(
        @Value("${notion.api.token:}") String notionToken,
        @Value("${notion.api.version}") String notionVersion,
        @Value("${notion.parent.page-id:}") String parentPageId
    ) {
        this.notionToken = notionToken;
        this.notionVersion = notionVersion;
        this.parentPageId = parentPageId;
        this.webClient = WebClient.builder()
            .baseUrl("https://api.notion.com/v1")
            .defaultHeader("Authorization", "Bearer " + notionToken)
            .defaultHeader("Notion-Version", notionVersion)
            .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
            .build();

        if (hasToken()) {
            LOGGER.info("NotionClient ready (token present, value hidden).");
        } else {
            LOGGER.info("NotionClient running without token; savePage will no-op.");
        }
        LOGGER.debug("Notion API version: {}", this.notionVersion);
    }

    public boolean hasToken() {
        return notionToken != null && !notionToken.isBlank();
    }

    public String savePage(String title, String content) {
        if (!hasToken()) {
            LOGGER.warn("Notion token missing; skipping savePage.");
            return null;
        }
        if (parentPageId == null || parentPageId.isBlank()) {
            LOGGER.warn("Notion parent page id missing; skipping savePage.");
            return null;
        }

        String safeTitle = (title == null || title.isBlank()) ? "AI Tutor Answer" : title;
        String safeContent = content == null ? "" : content;

        Map<String, Object> properties = Map.of(
            "title", Map.of(
                "title", List.of(
                    Map.of(
                        "text", Map.of("content", safeTitle)
                    )
                )
            )
        );

        Map<String, Object> paragraph = Map.of(
            "object", "block",
            "type", "paragraph",
            "paragraph", Map.of(
                "rich_text", List.of(
                    Map.of(
                        "type", "text",
                        "text", Map.of("content", safeContent)
                    )
                )
            )
        );

        Map<String, Object> payload = new HashMap<>();
        payload.put("parent", Map.of("type", "page_id", "page_id", parentPageId));
        payload.put("properties", properties);
        payload.put("children", List.of(paragraph));

        try {
            Mono<Map<String, Object>> resp = webClient.post()
                .uri("/pages")
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {});

            Map<String, Object> result = resp.block();
            if (result != null && result.get("id") != null) {
                String pageId = result.get("id").toString();
                LOGGER.info("Saved answer to Notion page {}", pageId);
                return pageId;
            }
        } catch (Exception ex) {
            LOGGER.error("Failed to save page to Notion", ex);
        }

        return null;
    }
}

