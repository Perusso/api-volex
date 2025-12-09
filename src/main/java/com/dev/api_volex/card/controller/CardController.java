package com.dev.api_volex.card.controller;

import com.dev.api_volex.card.dto.CardResponse;
import com.dev.api_volex.card.dto.CreateCardRequest;
import com.dev.api_volex.card.usecase.CreateCard;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cards")
public class CardController {

    private final CreateCard createCard;

    public CardController(CreateCard createCard) {
        this.createCard = createCard;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CardResponse create(
            @RequestHeader("x-api-key") String apiKey,
            @RequestBody CreateCardRequest request) {
        return createCard.execute(apiKey, request);
    }
}
