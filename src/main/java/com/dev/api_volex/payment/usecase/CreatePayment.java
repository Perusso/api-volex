package com.dev.api_volex.payment.usecase;

import com.dev.api_volex.business.entity.BusinessEntity;
import com.dev.api_volex.business.repository.BusinessRepository;
import com.dev.api_volex.card.entity.CardEntity;
import com.dev.api_volex.card.repository.CardRepository;
import com.dev.api_volex.card.usecase.CardExpirationValidator;
import com.dev.api_volex.card.usecase.CvcEncryptor;
import com.dev.api_volex.payment.dto.CreatePaymentRequest;
import com.dev.api_volex.payment.dto.CreatePaymentResponse;
import com.dev.api_volex.payment.entity.PaymentEntity;
import com.dev.api_volex.payment.repository.PaymentRepository;
import com.dev.api_volex.recharge.repository.RechargeRepository;
import com.dev.api_volex.shared.exceptions.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CreatePayment {

    private final PaymentRepository paymentRepository;
    private final CardRepository cardRepository;
    private final BusinessRepository businessRepository;
    private final RechargeRepository rechargeRepository;
    private final CardExpirationValidator expirationValidator;
    private final CvcEncryptor cvcEncryptor;

    public CreatePayment(
            PaymentRepository paymentRepository,
            CardRepository cardRepository,
            BusinessRepository businessRepository,
            RechargeRepository rechargeRepository,
            CardExpirationValidator expirationValidator,
            CvcEncryptor cvcEncryptor) {
        this.paymentRepository = paymentRepository;
        this.cardRepository = cardRepository;
        this.businessRepository = businessRepository;
        this.rechargeRepository = rechargeRepository;
        this.expirationValidator = expirationValidator;
        this.cvcEncryptor = cvcEncryptor;
    }

    public CreatePaymentResponse execute(CreatePaymentRequest request) {


        // 1. Validar cartão
        CardEntity card = validateCard(request.getCardId());

        // 2. Validar se cartão está ativo
        validateCardIsActive(card);

        // 3. Validar se cartão não está bloqueado
        validateCardNotBlocked(card);

        // 4. Validar se cartão não está expirado
        validateCardNotExpired(card);

        // 5. Validar senha do cartão
        validatePassword(card, request.getPassword());

        // 6. Validar estabelecimento
        BusinessEntity business = validateBusiness(request.getBusinessId());

        // 7. Validar compatibilidade de tipos (cartão x estabelecimento)
        validateCardBusinessTypeCompatibility(card, business);

        // 8. Validar saldo suficiente
        validateSufficientBalance(card, request.getAmount());

        // 9. Criar pagamento
        PaymentEntity payment = createPayment(request, card, business);


        return CreatePaymentResponse.builder()
                .id(payment.getId())
                .cardId(payment.getCard().getId())
                .businessId(payment.getBusiness().getId())
                .businessName(payment.getBusiness().getName())
                .amount(payment.getAmount())
                .timestamp(payment.getCreatedAt())
                .build();
    }

    // ============ VALIDAÇÕES ============

    private CardEntity validateCard(Long cardId) {
        return cardRepository.findById(cardId)
                .orElseThrow(() -> new CardNotFoundException(cardId));
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

    private void validatePassword(CardEntity card, String providedPassword) {
        String storedDecryptedPassword = cvcEncryptor.decrypt(card.getPassword());

        if (!storedDecryptedPassword.equals(providedPassword)) {
            throw new InvalidCardPasswordException();
        }
    }

    private BusinessEntity validateBusiness(Long businessId) {
        return businessRepository.findById(businessId)
                .orElseThrow(() -> new BusinessNotFoundException(businessId));
    }

    private void validateCardBusinessTypeCompatibility(CardEntity card, BusinessEntity business) {
        // Converter tipos para comparar
        String cardType = card.getType().getValue(); // Ex: "groceries"
        String businessType = business.getType().getValue();    // Ex: "groceries"

        // Mapeamento de tipos (pode precisar de ajuste dependendo dos valores)
        if (!isCompatible(cardType, businessType)) {
            throw new IncompatibleCardTypeException(card.getId(), cardType, business.getId(), businessType);
        }
    }

    private boolean isCompatible(String cardType, String businessType) {
        // Mapeia os tipos de cartão para os tipos de negócio compatíveis
        // Ajuste conforme necessário
        return cardType.equalsIgnoreCase(businessType);
    }

    private void validateSufficientBalance(CardEntity card, Long amount) {
        Long totalRecharges = rechargeRepository.sumAmountByCardId(card.getId());
        Long totalPayments = paymentRepository.sumAmountByCardId(card.getId());
        Long currentBalance = totalRecharges - totalPayments;

        if (currentBalance < amount) {
            throw new InsufficientBalanceException(card.getId(), currentBalance, amount);
        }
    }

    // ============ CRIAÇÃO ============

    private PaymentEntity createPayment(CreatePaymentRequest request, CardEntity card, BusinessEntity business) {
        PaymentEntity payment = new PaymentEntity();
        payment.setCard(card);
        payment.setBusiness(business);
        payment.setAmount(request.getAmount());

        return paymentRepository.save(payment);
    }
}
