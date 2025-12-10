package com.dev.api_volex.recharge.controller;

import com.dev.api_volex.recharge.dto.CreateRechargeRequest;
import com.dev.api_volex.recharge.dto.CreateRechargeResponse;
import com.dev.api_volex.recharge.usecase.CreateRecharge;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recharges")
public class RechargeController {

    private final CreateRecharge createRecharge;

    public RechargeController(CreateRecharge createRecharge) {
        this.createRecharge = createRecharge;
    }

    @PostMapping
    public ResponseEntity<CreateRechargeResponse> create(
            @RequestHeader("x-api-key") String apiKey,
            @RequestBody CreateRechargeRequest request) {

        CreateRechargeResponse response = createRecharge.execute(apiKey, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
