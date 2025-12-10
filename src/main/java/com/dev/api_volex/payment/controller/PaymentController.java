package com.dev.api_volex.payment.controller;

import com.dev.api_volex.payment.dto.CreatePaymentRequest;
import com.dev.api_volex.payment.dto.CreatePaymentResponse;
import com.dev.api_volex.payment.usecase.CreatePayment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final CreatePayment createPayment;

    public PaymentController(CreatePayment createPayment) {
        this.createPayment = createPayment;
    }

    @PostMapping
    public ResponseEntity<CreatePaymentResponse> create(
            @RequestBody CreatePaymentRequest request) {

        CreatePaymentResponse response = createPayment.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
