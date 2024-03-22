package com.money.domain.payment.service;

import com.money.domain.member.entity.Member;
import com.money.domain.member.exception.NotFoundMemberException;
import com.money.domain.member.repository.MemberRepository;
import com.money.domain.member.service.MemberService;
import com.money.domain.payment.dto.TodayDepositDto;
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
    private final MemberRepository memberRepository;
    private final MemberService memberService;

    public Page<TodayDepositDto> searchDescPageTodayDeposit(Long memberId, Pageable pageable) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(NotFoundMemberException::new);
        Team findTeam = memberService.getTeam(findMember);

        return paymentRepository.searchDescPageTodayDeposit(pageable, findTeam.getMembers());
    }
}
