package com.money.domain.payment.service;

import com.money.domain.member.entity.Member;
import com.money.domain.member.service.MemberService;
import com.money.domain.payment.entity.Deposit;
import com.money.domain.payment.entity.Payment;
import com.money.domain.payment.entity.Withdraw;
import com.money.domain.payment.entity.enums.PaymentType;
import com.money.domain.payment.exception.FailPaymentServiceException;
import com.money.domain.payment.exception.InvalidPaymentException;
import com.money.domain.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final MemberService memberService;

    @Transactional
    public Payment savePayment(Long memberId, String memo, Long amount, PaymentType paymentType, String type) {
        validatePayment(amount);
        Member findMember = memberService.findById(memberId);

        if (paymentType == PaymentType.DEPOSIT) {
            return paymentRepository.save(Deposit.of(findMember, memo, amount, type));
        }
        if (paymentType == PaymentType.WITHDRAW) {
            return paymentRepository.save(Withdraw.of(findMember, memo, amount, type));
        }
        throw new FailPaymentServiceException();
    }

    private void validatePayment(Long amount) {
        if (amount <= 0) {
            throw new InvalidPaymentException();
        }
    }
}
