package com.dev.api_volex.card.dto;


public class ActivateCardRequest {

    private String cvc;
    private String password;

    public String getCvc() {
        return cvc;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
