package com.money.domain.payment.service;

import com.money.domain.member.entity.Member;
import com.money.domain.member.exception.NotFoundMemberException;
import com.money.domain.member.repository.MemberRepository;
import com.money.domain.member.service.MemberService;
import com.money.domain.payment.dto.TodayDepositDto;
import com.money.domain.payment.dto.TodayWithdrawDto;
import com.money.domain.payment.entity.Deposit;
import com.money.domain.payment.entity.Payment;
import com.money.domain.payment.entity.Withdraw;
import com.money.domain.payment.exception.InvalidPaymentException;
import com.money.domain.payment.repository.PaymentRepository;
import com.money.domain.team.entity.Team;
import java.time.LocalDate;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final MemberService memberService;

    @Transactional
    public Payment saveDeposit(Long memberId, String memo, Long amount, String type) {
        validatePayment(amount);

        Member findMember = memberService.findById(memberId);
        Payment deposit = Deposit.of(findMember, memo, amount, type);

        return paymentRepository.save(deposit);
    }

    @Transactional
    public Payment saveWithdraw(Long memberId, String memo, Long amount, String type) {
        validatePayment(amount);

        Member findMember = memberService.findById(memberId);
        Payment withdraw = Withdraw.of(findMember, memo, amount, type);

        return paymentRepository.save(withdraw);
    }

    @Transactional
    public Page<TodayDepositDto> searchDescPageTodayDeposit(Long memberId, Pageable pageable) {
        Member findMember = memberService.findById(memberId);

        Team findTeam = memberService.getTeam(findMember);

        return paymentRepository.searchDescPageTodayDeposit(pageable, findTeam.getId());
    }

    @Transactional
    public Page<TodayWithdrawDto> searchDescPageTodayWithdraw(Long memberId, Pageable pageable) {
        Member findMember = memberService.findById(memberId);

        Team findTeam = memberService.getTeam(findMember);

        return paymentRepository.searchDescPageTodayWithdraw(pageable, findTeam.getId());
    }

    @Transactional
    public Long getTotalDepositPerMonth(Long memberId, LocalDate date) {
        Member findMember = memberService.findById(memberId);

        Team findTeam = memberService.getTeam(findMember);

        Long totalDepositPerMonth = paymentRepository.getTotalDepositPerMonth(date,
                findTeam.getId());

        return Objects.requireNonNullElse(totalDepositPerMonth, 0L);
    }

    @Transactional
    public Long getTotalWithdrawPerMonth(Long memberId, LocalDate date) {
        Member findMember = memberService.findById(memberId);

        Team findTeam = memberService.getTeam(findMember);

        Long totalDepositPerMonth = paymentRepository.getTotalWithdrawPerMonth(date,
                findTeam.getId());

        return Objects.requireNonNullElse(totalDepositPerMonth, 0L);
    }

    private void validatePayment(Long amount) {
        if (amount <= 0) {
            throw new InvalidPaymentException();
        }
    }
}
