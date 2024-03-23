package com.money.domain.payment.service;

import com.money.domain.member.entity.Member;
import com.money.domain.member.exception.NotFoundMemberException;
import com.money.domain.member.repository.MemberRepository;
import com.money.domain.member.service.MemberService;
import com.money.domain.payment.dto.TodayDepositDto;
import com.money.domain.payment.entity.Deposit;
import com.money.domain.payment.entity.Payment;
import com.money.domain.payment.repository.PaymentRepository;
import com.money.domain.team.entity.Team;
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
    private final MemberRepository memberRepository;

    public Payment saveDeposit(Long memberId, String memo, Long amount, String type) {
        Member findMember = getMember(memberId);

        Payment deposit = Deposit.of(findMember, memo, amount, type);

        return paymentRepository.save(deposit);
    }

    public Page<TodayDepositDto> searchDescPageTodayDeposit(Long memberId, Pageable pageable) {
        Member findMember = getMember(memberId);

        Team findTeam = memberService.getTeam(findMember);

        return paymentRepository.searchDescPageTodayDeposit(pageable, findTeam.getMembers());
    }

    private Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(NotFoundMemberException::new);
    }
}