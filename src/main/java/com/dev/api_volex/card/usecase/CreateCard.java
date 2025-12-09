package com.dev.api_volex.card.usecase;

import com.dev.api_volex.card.dto.CardResponse;
import com.dev.api_volex.card.usecase.adapter.CreateCardRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateCard {

    private final CreateCardRepository createCardRepository;

    public CreateCard(CreateCardRepository createCardRepository) {
        this.createCardRepository = createCardRepository;
    }

    public CardResponse execute(String apiKey, Object request) {
        // Implementação
        return null;
    }
}
