package com.money.controller;

import com.money.config.auth.MemberId;
import com.money.dto.response.payment.TodayPaymentRankResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "지출 API", description = "지출 관련 API")
@RequestMapping("/api/v1/payments")
@SecurityRequirement(name = "JWT")
public interface PaymentApi {

    @GetMapping("/today")
    ResponseEntity<TodayPaymentRankResponse> getTodayPaymentRank(
            @MemberId Long memberId,
            Pageable pageable
    );
}
