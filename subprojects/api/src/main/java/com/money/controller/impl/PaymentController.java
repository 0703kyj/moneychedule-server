package com.money.controller.impl;

import com.money.controller.PaymentApi;
import com.money.domain.payment.dto.TodayDepositDto;
import com.money.domain.payment.entity.Payment;
import com.money.domain.payment.service.PaymentService;
import com.money.dto.request.payment.DepositRequest;
import com.money.dto.response.payment.PaymentResponse;
import com.money.dto.response.payment.TodayDepositRankResponse;
import com.money.dto.response.payment.TotalMonthDepositResponse;
import com.money.exception.InvalidDateException;
import java.time.DateTimeException;
import java.time.LocalDate;
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
    public ResponseEntity<PaymentResponse> createDeposit(Long memberId, DepositRequest request) {
        Payment deposit = paymentService.saveDeposit(memberId, request.memo(), request.amount(),
                request.depositType());

        PaymentResponse response = PaymentResponse.from(deposit);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<TodayDepositRankResponse> getTodayDepositRank(Long memberId, Pageable pageable) {
        Page<TodayDepositDto> deposits = paymentService.searchDescPageTodayDeposit(memberId, pageable);
        TodayDepositRankResponse response = TodayDepositRankResponse.from(deposits);

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<TotalMonthDepositResponse> getTotalMonthDeposit(Long memberId,
            int year, int month) {

        LocalDate date = getLocalDate(year, month);
        Long totalDepositPerMonth = paymentService.getTotalDepositPerMonth(memberId, date);

        TotalMonthDepositResponse response = TotalMonthDepositResponse.of(
                date.getMonth().getValue(), totalDepositPerMonth);

        return ResponseEntity.ok(response);
    }

    private LocalDate getLocalDate(int year, int month) {
        try {
            return LocalDate.of(year, month, 1);
        } catch (DateTimeException e) {
            throw new InvalidDateException();
        }
    }
}
