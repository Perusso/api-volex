package com.dev.api_volex.card.usecase;

import com.dev.api_volex.card.dto.CardBalanceResponse;
import com.dev.api_volex.card.dto.RechargeResponse;
import com.dev.api_volex.card.dto.TransactionResponse;
import com.dev.api_volex.card.entity.CardEntity;
import com.dev.api_volex.card.repository.CardRepository;
import com.dev.api_volex.payment.entity.PaymentEntity;
import com.dev.api_volex.payment.repository.PaymentRepository;
import com.dev.api_volex.recharge.entity.RechargeEntity;
import com.dev.api_volex.recharge.repository.RechargeRepository;
import com.dev.api_volex.shared.exceptions.CardNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class GetCardBalance {

    private final CardRepository cardRepository;
    private final PaymentRepository paymentRepository;
    private final RechargeRepository rechargeRepository;

    public GetCardBalance(
            CardRepository cardRepository,
            PaymentRepository paymentRepository,
            RechargeRepository rechargeRepository) {
        this.cardRepository = cardRepository;
        this.paymentRepository = paymentRepository;
        this.rechargeRepository = rechargeRepository;
    }

    public CardBalanceResponse execute(Long cardId) {

        // 1. Validar se cartão existe
        CardEntity card = validateCardExists(cardId);

        // 2. Buscar recargas do cartão
        List<RechargeEntity> rechargeEntities = rechargeRepository.findAllByCardId(cardId);
        List<RechargeResponse> recharges = mapRechargesToResponse(rechargeEntities);

        // 3. Buscar transações (pagamentos) do cartão
        List<PaymentEntity> paymentEntities = paymentRepository.findAllByCardId(cardId);
        List<TransactionResponse> transactions = mapTransactionsToResponse(paymentEntities);

        // 4. Calcular saldo
        Long totalRecharges = rechargeRepository.sumAmountByCardId(cardId);
        Long totalPayments = paymentRepository.sumAmountByCardId(cardId);
        Long balance = totalRecharges - totalPayments;


        return CardBalanceResponse.builder()
                .balance(balance)
                .transactions(transactions)
                .recharges(recharges)
                .build();
    }

    // ============ VALIDAÇÕES ============

    private CardEntity validateCardExists(Long cardId) {
        return cardRepository.findById(cardId)
                .orElseThrow(() -> new CardNotFoundException(cardId));
    }

    // ============ MAPPERS ============

    private List<RechargeResponse> mapRechargesToResponse(List<RechargeEntity> rechargeEntities) {
        return rechargeEntities.stream()
                .map(this::mapRechargeToResponse)
                .collect(Collectors.toList());
    }

    private RechargeResponse mapRechargeToResponse(RechargeEntity recharge) {
        return RechargeResponse.builder()
                .id(recharge.getId())
                .cardId(recharge.getCard().getId())
                .timestamp(recharge.getCreatedAt())
                .amount(recharge.getAmount())
                .build();
    }

    private List<TransactionResponse> mapTransactionsToResponse(List<PaymentEntity> paymentEntities) {
        return paymentEntities.stream()
                .map(this::mapTransactionToResponse)
                .collect(Collectors.toList());
    }

    private TransactionResponse mapTransactionToResponse(PaymentEntity payment) {
        return TransactionResponse.builder()
                .id(payment.getId())
                .cardId(payment.getCard().getId())
                .businessId(payment.getBusiness().getId())
                .businessName(payment.getBusiness().getName())
                .timestamp(payment.getCreatedAt())
                .amount(payment.getAmount())
                .build();
    }
}
