package com.dev.api_volex.recharge.usecase;

import com.dev.api_volex.card.entity.CardEntity;
import com.dev.api_volex.card.repository.CardRepository;
import com.dev.api_volex.card.usecase.CardExpirationValidator;
import com.dev.api_volex.company.entity.CompanyEntity;
import com.dev.api_volex.company.repository.CompanyRepository;
import com.dev.api_volex.recharge.dto.CreateRechargeRequest;
import com.dev.api_volex.recharge.dto.CreateRechargeResponse;
import com.dev.api_volex.recharge.entity.RechargeEntity;
import com.dev.api_volex.recharge.repository.RechargeRepository;
import com.dev.api_volex.shared.exceptions.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CreateRecharge {

    private final RechargeRepository rechargeRepository;
    private final CardRepository cardRepository;
    private final CompanyRepository companyRepository;
    private final CardExpirationValidator expirationValidator;

    public CreateRecharge(
            RechargeRepository rechargeRepository,
            CardRepository cardRepository,
            CompanyRepository companyRepository,
            CardExpirationValidator expirationValidator) {
        this.rechargeRepository = rechargeRepository;
        this.cardRepository = cardRepository;
        this.companyRepository = companyRepository;
        this.expirationValidator = expirationValidator;
    }

    public CreateRechargeResponse execute(String apiKey, CreateRechargeRequest request) {

        // 1. Validar API Key
        CompanyEntity company = validateApiKey(apiKey);

        // 2. Validar cartão
        CardEntity card = validateCard(request.getCardId());

        // 3. Validar se cartão pertence à empresa
        validateCardCompany(card, company);

        // 4. Validar se cartão está ativo
        validateCardIsActive(card);

        // 5. Validar se cartão não está bloqueado
        validateCardNotBlocked(card);

        // 6. Validar se cartão não está expirado
        validateCardNotExpired(card);

        // 7. Criar recarga
        RechargeEntity recharge = createRecharge(request, card);


        return CreateRechargeResponse.builder()
                .id(recharge.getId())
                .cardId(recharge.getCard().getId())
                .amount(recharge.getAmount())
                .timestamp(recharge.getCreatedAt())
                .build();
    }

    // ============ VALIDAÇÕES ============

    private CompanyEntity validateApiKey(String apiKey) {
        return companyRepository.findByApiKey(apiKey)
                .orElseThrow(() -> new InvalidApiKeyException(apiKey));
    }

    private CardEntity validateCard(Long cardId) {
        return cardRepository.findById(cardId)
                .orElseThrow(() -> new CardNotFoundException(cardId));
    }

    private void validateCardCompany(CardEntity card, CompanyEntity company) {
        if (!card.getEmployee().getCompany().getId().equals(company.getId())) {
            throw new CardNotFromCompanyException(card.getId(), company.getId());
        }
    }

    private void validateCardIsActive(CardEntity card) {
        if (!card.isActive()) {
            throw new CardNotActiveException(card.getId());
        }
    }

    private void validateCardNotBlocked(CardEntity card) {
        if (card.isBlocked()) {
            throw new CardBlockedException(card.getId());
        }
    }

    private void validateCardNotExpired(CardEntity card) {
        if (expirationValidator.isExpired(card.getExpirationDate())) {
            throw new CardExpiredException(card.getId());
        }
    }

    // ============ CRIAÇÃO ============

    private RechargeEntity createRecharge(CreateRechargeRequest request, CardEntity card) {
        RechargeEntity recharge = new RechargeEntity();
        recharge.setCard(card);
        recharge.setAmount(request.getAmount());

        return rechargeRepository.save(recharge);
    }
}
