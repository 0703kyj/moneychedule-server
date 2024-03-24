package com.money.controller;

import com.money.config.auth.MemberId;
import com.money.dto.request.payment.DepositRequest;
import com.money.dto.response.payment.PaymentResponse;
import com.money.dto.response.payment.TodayDepositRankResponse;
import com.money.dto.response.payment.TotalMonthDepositResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "지출 API", description = "지출 관련 API")
@RequestMapping("/api/v1/payments")
@SecurityRequirement(name = "JWT")
public interface PaymentApi {

    @PostMapping("/deposit")
    ResponseEntity<PaymentResponse> createDeposit(
            @MemberId Long memberId,
            @RequestBody @Valid DepositRequest request
    );

    @GetMapping(value = "/today", params = {"page","size"})
    ResponseEntity<TodayDepositRankResponse> getTodayDepositRank(
            @MemberId Long memberId,
            Pageable pageable
    );

    @GetMapping("/deposit")
    ResponseEntity<TotalMonthDepositResponse> getTotalMonthDeposit(
            @MemberId Long memberId,
            @RequestParam
            @Parameter(description = "조회할 년", example = "2024")
            int year,
            @RequestParam
            @Parameter(description = "조회할 월", example = "3")
            int month
    );
}
