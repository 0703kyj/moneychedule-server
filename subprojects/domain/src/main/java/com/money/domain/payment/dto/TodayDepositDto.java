package com.money.domain.payment.dto;

import com.money.domain.payment.entity.enums.DepositType;

public record TodayDepositDto(
        Long id,
        Long memberId,
        String memo,
        Long amount,
        DepositType depositType
) {

}
