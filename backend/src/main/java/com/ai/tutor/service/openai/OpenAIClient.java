package com.ai.tutor.service.openai;

import java.time.Duration;
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
public class OpenAIClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(OpenAIClient.class);

    private final WebClient webClient;
    private final boolean hasApiKey;
    private final String model;
    private final Duration timeout;

    public OpenAIClient(
        @Value("${openai.api.key:}") String apiKey,
        @Value("${openai.model}") String model,
        @Value("${openai.timeout.seconds:60}") long timeoutSeconds
    ) {
        this.model = model;
        this.timeout = Duration.ofSeconds(timeoutSeconds);
        this.hasApiKey = apiKey != null && !apiKey.isBlank();
        this.webClient = WebClient.builder()
            .baseUrl("https://api.openai.com/v1")
            .defaultHeader("Authorization", "Bearer " + apiKey)
            .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
            .build();
        if (!this.hasApiKey) {
            LOGGER.warn("OpenAI API key is not configured; generateAnswer will return a fallback message.");
        }
    }

    public String generateAnswer(String question, String context) {
        if (!hasApiKey) {
            return "OpenAI API key not configured. Please set OPENAI_API_KEY.";
        }
        List<Map<String, String>> messages = List.of(
            Map.of("role", "system", "content", "You are a helpful AI tutor. Answer concisely with examples when helpful."),
            Map.of("role", "user", "content", (context != null && !context.isBlank()) ? context + "\n\nQuestion: " + question : question)
        );

        Map<String, Object> body = new HashMap<>();
        body.put("model", model);
        body.put("messages", messages);
        body.put("temperature", 0.2);
        body.put("max_tokens", 800);

        try {
            Mono<Map<String, Object>> resp = webClient.post()
                .uri("/chat/completions")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .timeout(timeout);

            Map<String, Object> result = resp.block(timeout);
            if (result == null) {
                LOGGER.warn("OpenAI returned empty response");
                return "OpenAI returned empty response";
            }

            Object choicesObj = result.get("choices");
            if (choicesObj instanceof List<?> choices && !choices.isEmpty()) {
                Object firstObj = choices.get(0);
                if (firstObj instanceof Map<?, ?> first) {
                    Object message = first.get("message");
                    if (message instanceof Map<?, ?> messageMap) {
                        Object content = messageMap.get("content");
                        return content != null ? content.toString().trim() : "No content from OpenAI";
                    } else if (first.get("text") != null) {
                        return first.get("text").toString().trim();
                    }
                }
            }
            return "Unable to parse OpenAI response";
        } catch (Exception e) {
            LOGGER.error("Error calling OpenAI", e);
            return "Error calling OpenAI: " + e.getMessage();
        }
    }
}

