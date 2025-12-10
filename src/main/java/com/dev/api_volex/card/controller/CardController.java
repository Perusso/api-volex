package com.dev.api_volex.card.controller;

import com.dev.api_volex.card.dto.*;
import com.dev.api_volex.card.usecase.*;
import jdk.jfr.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cards")
public class CardController {

    private final CreateCard createCard;
    private final ActivateCard activateCard;
    private final BlockCard blockCard;
    private final UnblockCard unblockCard;
    private final GetCardBalance getCardBalance;

    public CardController(CreateCard createCard, ActivateCard activateCard, BlockCard blockCard, UnblockCard unblockCard, GetCardBalance getCardBalance) {
        this.activateCard = activateCard;
        this.createCard = createCard;
        this.blockCard = blockCard;
        this.unblockCard = unblockCard;
        this.getCardBalance = getCardBalance;
    }

    @PostMapping
    @Description("Criar um novo cartão" )
    public ResponseEntity<CardResponse> create(
            @RequestHeader("x-api-key") String apiKey,
            @RequestBody CreateCardRequest request) {

        CardResponse response = createCard.execute(apiKey, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/{cardId}/activate")
    @Description("Ativar um cartão" )
    public ResponseEntity<ActivateCardResponse> activate(
            @PathVariable Long cardId,
            @RequestBody ActivateCardRequest request) {

        ActivateCardResponse response = activateCard.execute(cardId, request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{cardId}/block")
    @Description("Bloquear um cartão" )
    public ResponseEntity<BlockCardResponse> block(
            @PathVariable Long cardId,
            @RequestBody BlockCardRequest request) {

        BlockCardResponse response = blockCard.execute(cardId, request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{cardId}/unblock")
    @Description("Desbloquear um cartão bloqueado" )
    public ResponseEntity<UnblockCardResponse> unblock(
            @PathVariable Long cardId,
            @RequestBody UnblockCardRequest request) {

        UnblockCardResponse response = unblockCard.execute(cardId, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{cardId}/balance")
    @Description("Obter o saldo de um cartão" )
    public ResponseEntity<CardBalanceResponse> getBalance(
            @PathVariable Long cardId) {

        CardBalanceResponse response = getCardBalance.execute(cardId);
        return ResponseEntity.ok(response);
    }
}
