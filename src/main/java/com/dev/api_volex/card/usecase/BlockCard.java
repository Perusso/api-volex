package com.dev.api_volex.card.usecase;

import com.dev.api_volex.card.dto.BlockCardRequest;
import com.dev.api_volex.card.dto.BlockCardResponse;
import com.dev.api_volex.card.entity.CardEntity;
import com.dev.api_volex.card.repository.CardRepository;
import com.dev.api_volex.shared.exceptions.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BlockCard {

    private final CardRepository cardRepository;
    private final CvcEncryptor cvcEncryptor;
    private final CardExpirationValidator expirationValidator;

    public BlockCard(
            CardRepository cardRepository,
            CvcEncryptor cvcEncryptor,
            CardExpirationValidator expirationValidator) {
        this.cardRepository = cardRepository;
        this.cvcEncryptor = cvcEncryptor;
        this.expirationValidator = expirationValidator;
    }

    public BlockCardResponse execute(Long cardId, BlockCardRequest request) {


        // 1. Validar se cartão existe
        CardEntity card = validateCardExists(cardId);

        // 2. Validar se cartão não está expirado
        validateCardNotExpired(card);

        // 3. Validar se cartão não está bloqueado
        validateCardNotBlocked(card);

        // 4. Validar senha do cartão
        validatePassword(card, request.getPassword());

        // 5. Bloquear cartão
        blockCard(card);


        return BlockCardResponse.builder()
                .cardId(card.getId())
                .message("Cartão bloqueado com sucesso")
                .isBlocked(true)
                .build();
    }

    // ============ VALIDAÇÕES ============

    private CardEntity validateCardExists(Long cardId) {
        return cardRepository.findById(cardId)
                .orElseThrow(() -> new CardNotFoundException(cardId));
    }

    private void validateCardNotExpired(CardEntity card) {
        if (expirationValidator.isExpired(card.getExpirationDate())) {
            throw new CardExpiredException(card.getId());
        }
    }

    private void validateCardNotBlocked(CardEntity card) {
        if (card.isBlocked()) {
            throw new CardAlreadyBlockedException(card.getId());
        }
    }

    private void validatePassword(CardEntity card, String providedPassword) {
        // Verificar se o cartão tem senha (está ativo)
        if (!card.isActive()) {
            throw new CardNotActiveException(card.getId());
        }

        // Descriptografar senha armazenada
        String storedDecryptedPassword = cvcEncryptor.decrypt(card.getPassword());

        // Comparar com senha fornecida
        if (!storedDecryptedPassword.equals(providedPassword)) {
            throw new InvalidCardPasswordException();
        }
    }

    // ============ ATUALIZAÇÃO ============

    private void blockCard(CardEntity card) {
        card.setBlocked(true);
        cardRepository.save(card);
    }
}
