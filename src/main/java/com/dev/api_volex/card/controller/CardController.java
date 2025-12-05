package com.dev.api_volex.card.controller;

import com.dev.api_volex.card.dto.CardResponse;
import com.dev.api_volex.card.dto.CreateCardRequest;
import com.dev.api_volex.card.usecase.CreateCardUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cards")
public class CardController {

    private final CreateCardUseCase createCardUseCase;

    public CardController(CreateCardUseCase createCardUseCase) {
        this.createCardUseCase = createCardUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CardResponse create(
            @RequestHeader("x-api-key") String apiKey,
            @RequestBody CreateCardRequest request) {
        // Implementação
        return createCardUseCase.execute(apiKey, request);
    }
}
