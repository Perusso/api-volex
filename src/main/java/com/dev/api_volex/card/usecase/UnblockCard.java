package com.dev.api_volex.card.usecase;

import com.dev.api_volex.card.dto.UnblockCardRequest;
import com.dev.api_volex.card.dto.UnblockCardResponse;
import com.dev.api_volex.card.entity.CardEntity;
import com.dev.api_volex.card.repository.CardRepository;
import com.dev.api_volex.shared.exceptions.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UnblockCard {

    private final CardRepository cardRepository;
    private final CvcEncryptor cvcEncryptor;
    private final CardExpirationValidator expirationValidator;

    public UnblockCard(
            CardRepository cardRepository,
            CvcEncryptor cvcEncryptor,
            CardExpirationValidator expirationValidator) {
        this.cardRepository = cardRepository;
        this.cvcEncryptor = cvcEncryptor;
        this.expirationValidator = expirationValidator;
    }

    public UnblockCardResponse execute(Long cardId, UnblockCardRequest request) {


        // 1. Validar se cartão existe
        CardEntity card = validateCardExists(cardId);

        // 2. Validar se cartão não está expirado
        validateCardNotExpired(card);

        // 3. Validar se cartão está bloqueado
        validateCardIsBlocked(card);

        // 4. Validar senha do cartão
        validatePassword(card, request.getPassword());

        // 5. Desbloquear cartão
        unblockCard(card);


        return UnblockCardResponse.builder()
                .cardId(card.getId())
                .message("Cartão desbloqueado com sucesso")
                .isBlocked(false)
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

    private void validateCardIsBlocked(CardEntity card) {
        if (!card.isBlocked()) {
            throw new CardNotBlockedException(card.getId());
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

    private void unblockCard(CardEntity card) {
        card.setBlocked(false);
        cardRepository.save(card);
    }
}
