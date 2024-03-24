package com.money.domain.service;

import com.money.domain.member.entity.Member;
import com.money.domain.member.entity.enums.Platform;
import com.money.domain.payment.exception.InvalidPaymentException;
import com.money.domain.payment.repository.PaymentRepository;
import com.money.domain.payment.service.PaymentService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @InjectMocks
    PaymentService paymentService;

    @Mock
    PaymentRepository paymentRepository;

    @ParameterizedTest
    @ValueSource(longs = {0,-100,-1000,-10000,-100000,-1000000})
    void 입금_금액이_0이하인_경우_예외를_던진다(Long amount) {

        Member member = Member.of("abc@naver.com", "fdsf", Platform.EMAIL);

        Long memberId = member.getId();
        Assertions.assertThatThrownBy(() -> paymentService.saveDeposit(memberId, "memo", amount, "용돈"))
                .isInstanceOf(InvalidPaymentException.class);
    }

    @ParameterizedTest
    @ValueSource(longs = {0,-100,-1000,-10000,-100000,-1000000})
    void 출금_금액이_0이하인_경우_예외를_던진다(Long amount) {

        Member member = Member.of("abc@naver.com", "fdsf", Platform.EMAIL);

        Long memberId = member.getId();
        Assertions.assertThatThrownBy(() -> paymentService.saveWithdraw(memberId, "memo", amount, "식비"))
                .isInstanceOf(InvalidPaymentException.class);
    }
}
