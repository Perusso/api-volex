package com.dev.api_volex.card.usecase;

import com.dev.api_volex.card.dto.ActivateCardRequest;
import com.dev.api_volex.card.dto.ActivateCardResponse;
import com.dev.api_volex.card.entity.CardEntity;
import com.dev.api_volex.card.repository.CardRepository;
import com.dev.api_volex.shared.exceptions.CardAlreadyActiveException;
import com.dev.api_volex.shared.exceptions.CardExpiredException;
import com.dev.api_volex.shared.exceptions.CardNotFoundException;
import com.dev.api_volex.shared.exceptions.InvalidCvcException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ActivateCard {

    private final CardRepository cardRepository;
    private final CvcEncryptor cvcEncryptor;
    private final CardExpirationValidator expirationValidator;

    public ActivateCard(
            CardRepository cardRepository,
            CvcEncryptor cvcEncryptor,
            CardExpirationValidator expirationValidator) {
        this.cardRepository = cardRepository;
        this.cvcEncryptor = cvcEncryptor;
        this.expirationValidator = expirationValidator;
    }

    public ActivateCardResponse execute(Long cardId, ActivateCardRequest request) {


        // 1. Validar se cartão existe
        CardEntity card = validateCardExists(cardId);

        // 2. Validar se cartão não está expirado
        validateCardNotExpired(card);

        // 3. Validar se cartão não está ativo
        validateCardNotActive(card);

        // 4. Validar CVC
        validateCvc(card, request.getCvc());

        // 5. Criptografar senha
        String encryptedPassword = cvcEncryptor.encrypt(request.getPassword());

        // 6. Atualizar cartão
        updateCard(card, encryptedPassword);


        return ActivateCardResponse.builder()
                .cardId(card.getId())
                .message("Cartão ativado com sucesso")
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

    private void validateCardNotActive(CardEntity card) {
        if (card.isActive()) {
            throw new CardAlreadyActiveException(card.getId());
        }
    }

    private void validateCvc(CardEntity card, String providedCvc) {
        // Descriptografar CVC armazenado
        String storedDecryptedCvc = cvcEncryptor.decrypt(card.getSecurityCode());

        // Comparar com CVC fornecido
        if (!storedDecryptedCvc.equals(providedCvc)) {
            throw new InvalidCvcException();
        }
    }

    // ============ ATUALIZAÇÃO ============

    private void updateCard(CardEntity card, String encryptedPassword) {
        card.setActive(true);
        card.setPassword(encryptedPassword);
        cardRepository.save(card);
    }
}
