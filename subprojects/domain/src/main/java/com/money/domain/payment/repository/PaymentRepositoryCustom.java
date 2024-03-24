package com.money.domain.payment.repository;

import com.money.domain.payment.dto.TodayDepositDto;
import com.money.domain.payment.dto.TodayWithdrawDto;
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PaymentRepositoryCustom {

    Page<TodayDepositDto> searchDescPageTodayDeposit(Pageable pageable, Long teamId);
    Page<TodayWithdrawDto> searchDescPageTodayWithdraw(Pageable pageable, Long teamId);

    Long getTotalDepositPerMonth(LocalDate month, Long teamId);
    Long getTotalWithdrawPerMonth(LocalDate month, Long teamId);
}
