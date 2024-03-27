package com.money.service.payment;

import com.money.domain.member.entity.Member;
import com.money.domain.member.service.MemberService;
import com.money.domain.payment.entity.enums.PaymentType;
import com.money.domain.payment.repository.PaymentRepository;
import com.money.domain.team.entity.Team;
import java.time.LocalDate;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PaymentAmountService {

    private final PaymentRepository paymentRepository;
    private final MemberService memberService;

    @Transactional
    public Long getTotalPaymentPerMonth(Long memberId, LocalDate date, PaymentType paymentType) {
        Member findMember = memberService.findById(memberId);
        Team findTeam = memberService.getTeam(findMember);

        Long totalPaymentPerMonth = 0L;
        if (paymentType == PaymentType.DEPOSIT) {
            totalPaymentPerMonth = paymentRepository.getTotalDepositPerMonth(date,
                    findTeam.getId());
        }
        if (paymentType == PaymentType.WITHDRAW) {
            totalPaymentPerMonth = paymentRepository.getTotalWithdrawPerMonth(date,
                    findTeam.getId());
        }
        return Objects.requireNonNullElse(totalPaymentPerMonth, 0L);
    }
}
