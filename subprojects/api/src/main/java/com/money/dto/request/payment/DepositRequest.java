package com.money.dto.request.payment;

public record DepositRequest(
        String memo,
        Long amount,
        String depositType
) {

}
