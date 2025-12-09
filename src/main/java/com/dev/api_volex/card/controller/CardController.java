package com.dev.api_volex.card.controller;

import com.dev.api_volex.card.dto.*;
import com.dev.api_volex.card.usecase.ActivateCard;
import com.dev.api_volex.card.usecase.BlockCard;
import com.dev.api_volex.card.usecase.CreateCard;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cards")
public class CardController {

    private final CreateCard createCard;
    private final ActivateCard activateCard;
    private final BlockCard blockCard;

    public CardController(CreateCard createCard, ActivateCard activateCard, BlockCard blockCard) {
        this.activateCard = activateCard;
        this.createCard = createCard;
        this.blockCard = blockCard;
    }

    @PostMapping
    public ResponseEntity<CardResponse> create(
            @RequestHeader("x-api-key") String apiKey,
            @RequestBody CreateCardRequest request) {

        CardResponse response = createCard.execute(apiKey, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/{cardId}/activate")
    public ResponseEntity<ActivateCardResponse> activate(
            @PathVariable Long cardId,
            @RequestBody ActivateCardRequest request) {

        ActivateCardResponse response = activateCard.execute(cardId, request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{cardId}/block")
    public ResponseEntity<BlockCardResponse> block(
            @PathVariable Long cardId,
            @RequestBody BlockCardRequest request) {

        BlockCardResponse response = blockCard.execute(cardId, request);
        return ResponseEntity.ok(response);
    }
}
