package com.money.controller.impl;

import com.money.controller.PaymentApi;
import com.money.domain.payment.dto.TodayDepositDto;
import com.money.domain.payment.service.PaymentService;
import com.money.dto.response.payment.TodayPaymentRankResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class PaymentController implements PaymentApi {

    private final PaymentService paymentService;


    @Override
    public ResponseEntity<TodayPaymentRankResponse> getTodayPaymentRank(Long memberId, Pageable pageable) {
        Page<TodayDepositDto> deposits = paymentService.searchDescPageTodayDeposit(memberId, pageable);
        TodayPaymentRankResponse response = TodayPaymentRankResponse.from(deposits);

        return ResponseEntity.ok(response);
    }
}
