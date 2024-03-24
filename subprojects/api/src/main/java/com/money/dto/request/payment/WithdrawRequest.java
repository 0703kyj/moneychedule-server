package com.money.dto.request.payment;

public record WithdrawRequest(
        String memo,
        Long amount,
        String withdrawType
) {

}
