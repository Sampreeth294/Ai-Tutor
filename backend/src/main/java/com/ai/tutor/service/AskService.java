package com.ai.tutor.service;

import com.ai.tutor.dto.AskResponse;
import com.ai.tutor.service.notion.NotionClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AskService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AskService.class);
    private static final String SAMPLE_PAGE_ID = "placeholder-page-id";

    private final NotionClient notionClient;

    public AskService(NotionClient notionClient) {
        this.notionClient = notionClient;
    }

    public AskResponse ask(String question) {
        String sanitizedQuestion = question == null ? "" : question.trim();
        LOGGER.info("Received /api/ask request. Has question: {}", !sanitizedQuestion.isEmpty());

        if (notionClient.hasToken()) {
            LOGGER.info("Notion token present; delegating to NotionClient skeleton.");
            notionClient.queryPage(SAMPLE_PAGE_ID);
        } else {
            LOGGER.info("Notion token absent; returning stub response.");
        }

        String answer = "This is a placeholder answer for: " + sanitizedQuestion;
        return new AskResponse(answer);
    }
}

