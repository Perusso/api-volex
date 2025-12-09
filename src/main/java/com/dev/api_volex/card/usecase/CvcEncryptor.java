package com.dev.api_volex.card.usecase;

import org.jasypt.encryption.StringEncryptor;
import org.springframework.stereotype.Component;

@Component
public class CvcEncryptor {

    private final StringEncryptor stringEncryptor;

    public CvcEncryptor(StringEncryptor stringEncryptor) {
        this.stringEncryptor = stringEncryptor;
    }

    public String encrypt(String cvc) {
        return stringEncryptor.encrypt(cvc);
    }

    public String decrypt(String encryptedCvc) {
        return stringEncryptor.decrypt(encryptedCvc);
    }
}
