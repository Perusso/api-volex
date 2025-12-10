package com.dev.api_volex.shared.exceptions;

import org.springframework.http.HttpStatus;

public class CardNotFromCompanyException extends BusinessException {
    public CardNotFromCompanyException(Long cardId, Long companyId) {
        super("Cartão " + cardId + " não pertence à empresa " + companyId,
                HttpStatus.BAD_REQUEST);
    }
}
