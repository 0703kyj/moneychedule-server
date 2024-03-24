package com.money.domain.payment.entity.enums;

import com.money.domain.payment.exception.NotFoundDepositTypeException;

public enum DepositType {
    SALARY("월급"),
    PIN_MONEY("용돈"),
    CARRY("이월"),
    BALANCE_ADJUSTMENT("잔액 조정"),
    ASSET_WITHDRAWAL("자산 인출");

    private String value;
    DepositType(String value) {
        this.value = value;
    }

    public static DepositType fromString(String value) {
        for (DepositType deposit : DepositType.values()) {
            if (deposit.value.equalsIgnoreCase(value)) {
                return deposit;
            }
        }
        throw new NotFoundDepositTypeException();
    }
}
