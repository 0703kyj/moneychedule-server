package com.money.domain.payment.entity.enums;

import com.money.domain.payment.exception.NotFoundWithdrawTypeException;

public enum WithdrawType {
    SALARY("월급"),
    PIN_MONEY("용돈"),
    CARRY("이월"),
    BALANCE_ADJUSTMENT("잔액 조정"),
    ASSET_WITHDRAWAL("자산 인출");

    private String value;
    WithdrawType(String value) {
        this.value = value;
    }

    public WithdrawType fromString(String value) {
        try {
            return WithdrawType.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new NotFoundWithdrawTypeException();
        }
    }
}
