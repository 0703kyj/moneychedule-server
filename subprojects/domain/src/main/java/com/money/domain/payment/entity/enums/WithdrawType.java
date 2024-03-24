package com.money.domain.payment.entity.enums;

import com.money.domain.payment.exception.NotFoundWithdrawTypeException;

public enum WithdrawType {
    FOOD("식비"),
    TRANSPORTATION("교통비"),
    CAFE("카페"),
    COMMUNICATION("통신비"),
    CLOTH("의류"),
    DAILY("생필품"),
    CULTURAL("문화생활"),
    INSURANCE("보험"),
    DUES("회비"),
    EDUCATION("교육비"),
    TAX("세금"),
    BEAUTY("미용"),
    HEALTH("의료/건강"),
    EXERCISE("운동"),
    CONGRATULATIONS("경조사"),
    SAVING("저축"),
    MAINTENANCE("공과금"),
    HOME_APPLIANCES("가전"),
    CARD_FEE("카드대금"),
    ETC("기타");

    private String value;
    WithdrawType(String value) {
        this.value = value;
    }

    public static WithdrawType fromString(String value) {
        for (WithdrawType withdraw : WithdrawType.values()) {
            if (withdraw.value.equalsIgnoreCase(value)) {
                return withdraw;
            }
        }
        throw new NotFoundWithdrawTypeException();
    }
}
