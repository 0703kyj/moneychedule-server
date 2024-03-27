package com.money.service.payment;

import com.money.domain.member.entity.Member;
import com.money.domain.member.service.MemberService;
import com.money.domain.payment.dto.TodayDepositDto;
import com.money.domain.payment.dto.TodayWithdrawDto;
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
public class PaymentPagingService {

    private final PaymentRepository paymentRepository;
    private final MemberService memberService;

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
}
