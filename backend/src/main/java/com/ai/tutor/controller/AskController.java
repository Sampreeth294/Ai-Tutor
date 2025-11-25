package com.ai.tutor.controller;

import com.ai.tutor.dto.AskRequest;
import com.ai.tutor.dto.AskResponse;
import com.ai.tutor.service.AskService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/ask", produces = MediaType.APPLICATION_JSON_VALUE)
public class AskController {

    private final AskService askService;

    public AskController(AskService askService) {
        this.askService = askService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AskResponse> ask(@RequestBody(required = false) AskRequest request) {
        if (request == null || request.getQuestion() == null || request.getQuestion().isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        AskResponse response = askService.getAnswer(request);
        return ResponseEntity.ok(response);
    }
}

