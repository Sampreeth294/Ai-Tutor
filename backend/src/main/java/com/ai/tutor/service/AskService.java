package com.ai.tutor.service;

import com.ai.tutor.dto.AskRequest;
import com.ai.tutor.dto.AskResponse;
import com.ai.tutor.service.notion.NotionClient;
import com.ai.tutor.service.openai.OpenAIClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AskService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AskService.class);

    private final OpenAIClient openAIClient;
    private final NotionClient notionClient;

    public AskService(OpenAIClient openAIClient, NotionClient notionClient) {
        this.openAIClient = openAIClient;
        this.notionClient = notionClient;
    }

    public AskResponse getAnswer(AskRequest request) {
        String question = request == null ? "" : sanitize(request.getQuestion());
        String context = request == null ? "" : sanitize(request.getContext());

        LOGGER.info("Processing /api/ask question. Length: {}", question.length());

        String answer = openAIClient.generateAnswer(question, context);
        if (answer == null || answer.isBlank()) {
            LOGGER.warn("OpenAI returned empty answer; using fallback message.");
            answer = "Unable to generate an answer right now. Please try again later.";
        }

        String notionPageId = notionClient.savePage("Tutor: " + question, answer);

        AskResponse response = new AskResponse();
        response.setAnswer(answer);
        response.setSource("openai");
        response.setRaw(null);
        response.setNotionPageId(notionPageId);

        LOGGER.info("Answer ready. Notion page created: {}", notionPageId != null);
        return response;
    }

    private String sanitize(String value) {
        return value == null ? "" : value.trim();
    }
}

