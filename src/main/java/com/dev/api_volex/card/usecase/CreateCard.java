package com.dev.api_volex.card.usecase;

import com.dev.api_volex.card.dto.CardResponse;
import com.dev.api_volex.card.dto.CreateCardRequest;
import com.dev.api_volex.card.entity.CardEntity;
import com.dev.api_volex.card.enumeration.CardType;
import com.dev.api_volex.card.repository.CardRepository;
import com.dev.api_volex.company.entity.CompanyEntity;
import com.dev.api_volex.company.usecase.GetCompany;
import com.dev.api_volex.employee.entity.EmployeeEntity;
import com.dev.api_volex.employee.usecase.GetEmployee;
import com.dev.api_volex.shared.exceptions.DuplicateCardException;
import com.dev.api_volex.shared.exceptions.EmployeeNotFoundException;
import com.dev.api_volex.shared.exceptions.InvalidCardTypeException;
import org.springframework.stereotype.Service;

@Service
public class CreateCard {

    private final CardRepository cardRepository;
    private final GenerateCreditCardData generateCreditCardData;
    private final FormatCardholderName formatCardholderName;
    private final GetCompany getCompany;
    private final GetEmployee getEmployee;
    private final CvcEncryptor cvcEncryptor;

    public CreateCard(CardRepository cardRepository, GenerateCreditCardData generateCreditCardData, FormatCardholderName formatCardholderName, GetCompany getCompany, GetEmployee getEmployee, CvcEncryptor cvcEncryptor) {
        this.cardRepository = cardRepository;
        this.generateCreditCardData = generateCreditCardData;
        this.formatCardholderName = formatCardholderName;
        this.getCompany = getCompany;
        this.getEmployee = getEmployee;
        this.cvcEncryptor = cvcEncryptor;
    }

    public CardResponse execute(String apiKey, CreateCardRequest request) {
        validateCardCreation(apiKey, request);
        GeneratedCardData generatedCardData = generateCardData(request);
        CardEntity savedCard = persist(request, generatedCardData.cardNumber(), generatedCardData.cvv(), generatedCardData.cardholderName(), generatedCardData.expirationDate(), generatedCardData.employee);
        return buildResponse(savedCard);
    }

    private void validateCardCreation(String apiKey, CreateCardRequest request) {
        validateEmployee(request.getEmployeeId());
        validateEmployeeCompany(getEmployee.findById(request.getEmployeeId()), getCompany.findByApiKey(apiKey));
        validateCardType(request.getCardType());
        validateDuplicateCard(request.getEmployeeId(), request.getCardType());
        validateApiKey(apiKey);
    }

    private record GeneratedCardData(String cardNumber, String cvv, String expirationDate, String cardholderName, EmployeeEntity employee) {
    }

    private GeneratedCardData generateCardData(CreateCardRequest request) {
        String cardNumber = generateCreditCardData.generateCreditCardNumber();
        String cvv = generateCreditCardData.generateCreditCardCvv();
        String expirationDate = generateCreditCardData.generateExpirationDate();
        EmployeeEntity employee = getEmployee.findById(request.getEmployeeId());
        String cardholderName = formatCardholderName.format(employee.getFullName());
        return new GeneratedCardData(cardNumber, cvv, expirationDate, cardholderName, employee);
    }

    private CardEntity persist(CreateCardRequest request, String cardNumber, String cvv, String cardholderName, String expirationDate, EmployeeEntity employee) {
        CardEntity entity = new CardEntity();
        entity.setCardNumber(cardNumber);
        String encryptedCvv = cvcEncryptor.encrypt(cvv);
        entity.setSecurityCode(encryptedCvv);
        entity.setCardHolderName(cardholderName);
        entity.setExpirationDate(expirationDate);
        entity.setType(CardType.fromString(request.getCardType()));
        entity.setBlocked(true);
        entity.setVirtual(false);
        entity.setEmployee(employee);
        return cardRepository.save(entity);
    }


    private void validateCardType(String cardType) {
        if (!CardType.isValid(cardType)) {
            throw new InvalidCardTypeException(cardType);
        }
    }

    private void validateApiKey(String apiKey) {
        getCompany.findByApiKey(apiKey);
    }

    private void validateEmployee(Long employeeId) {
        getEmployee.findById(employeeId);
    }

    private void validateEmployeeCompany(EmployeeEntity employee, CompanyEntity company) {
        if (!employee.getCompany().getId().equals(company.getId())) {
            throw new EmployeeNotFoundException(employee.getId());
        }
    }

    private void validateDuplicateCard(Long employeeId, String cardType) {
        CardType type = CardType.fromString(cardType);
        boolean exists = cardRepository.existsByEmployeeIdAndType(employeeId, type);
        if (exists) {
            throw new DuplicateCardException(employeeId, cardType);
        }
    }

    private CardResponse buildResponse(CardEntity card) {
        return CardResponse.builder()
                .id(card.getId())
                .cardNumber(card.getCardNumber())
                .cardholderName(card.getCardHolderName())
                .expirationDate(card.getExpirationDate())
                .type(card.getType().getValue())
                .isBlocked(card.isBlocked())
                .build();
    }
}
