package com.money.domain.entity;

import com.money.domain.payment.entity.enums.DepositType;
import com.money.domain.payment.entity.enums.WithdrawType;
import com.money.domain.payment.exception.NotFoundDepositTypeException;
import com.money.domain.payment.exception.NotFoundWithdrawTypeException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class PaymentTest {

    @Test
    void 입금_타입이_잘못된_경우_예외를_반환한다() {
        String withdrawType = "식비";
        String depositType = "용돈";

        Assertions.assertThatThrownBy(() -> DepositType.fromString(withdrawType))
                .isInstanceOf(NotFoundDepositTypeException.class);

        Assertions.assertThatThrownBy(() -> WithdrawType.fromString(depositType))
                .isInstanceOf(NotFoundWithdrawTypeException.class);
    }
}
