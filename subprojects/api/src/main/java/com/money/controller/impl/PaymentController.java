package com.money.controller.impl;

import com.money.controller.PaymentApi;
import com.money.domain.payment.dto.TodayDepositDto;
import com.money.domain.payment.dto.TodayWithdrawDto;
import com.money.domain.payment.entity.Payment;
import com.money.domain.payment.entity.enums.PaymentType;
import com.money.domain.payment.service.PaymentService;
import com.money.dto.request.payment.PaymentRequest;
import com.money.dto.response.payment.PaymentResponse;
import com.money.dto.response.payment.TodayDepositRankResponse;
import com.money.dto.response.payment.TodayWithdrawRankResponse;
import com.money.dto.response.payment.TotalMonthPaymentResponse;
import com.money.service.payment.PaymentAmountService;
import com.money.service.payment.PaymentPagingService;
import com.money.util.LocalDateConverter;
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
    private final PaymentAmountService paymentAmountService;
    private final PaymentPagingService paymentPagingService;

    @Override
    public ResponseEntity<PaymentResponse> createPayment(Long memberId, String type,
            PaymentRequest request) {

        PaymentType paymentType = PaymentType.fromString(type);

        Payment deposit = paymentService.savePayment(memberId, request.memo(), request.amount(),
                paymentType, request.type());
        PaymentResponse response = PaymentResponse.from(deposit);

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<TotalMonthPaymentResponse> getTotalMonthPayment(Long memberId,
            String type, int year, int month) {
        PaymentType paymentType = PaymentType.fromString(type);

        LocalDate date = LocalDateConverter.getLocalDate(year, month);
        Long totalWithdrawPerMonth = paymentAmountService.getTotalPaymentPerMonth(memberId, date, paymentType);

        TotalMonthPaymentResponse response = TotalMonthPaymentResponse.of(
                date.getMonth().getValue(), totalWithdrawPerMonth);

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<TodayDepositRankResponse> getTodayDepositRank(Long memberId,
            Pageable pageable) {
        Page<TodayDepositDto> deposits = paymentPagingService.searchDescPageTodayDeposit(memberId,
                pageable);
        TodayDepositRankResponse response = TodayDepositRankResponse.from(deposits);

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<TodayWithdrawRankResponse> getTodayWithdrawRank(Long memberId,
            Pageable pageable) {
        Page<TodayWithdrawDto> withdraws = paymentPagingService.searchDescPageTodayWithdraw(memberId,
                pageable);
        TodayWithdrawRankResponse response = TodayWithdrawRankResponse.from(withdraws);

        return ResponseEntity.ok(response);
    }
}
