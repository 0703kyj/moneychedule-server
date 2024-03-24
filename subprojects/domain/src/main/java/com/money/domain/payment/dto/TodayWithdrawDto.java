package com.money.domain.payment.dto;

import com.money.domain.payment.entity.enums.WithdrawType;

public record TodayWithdrawDto(
        Long id,
        Long memberId,
        String memo,
        Long amount,
        WithdrawType withdrawType
) {

}
