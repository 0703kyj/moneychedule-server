package com.money.domain.payment.repository;

import com.money.domain.member.entity.Member;
import com.money.domain.payment.dto.TodayDepositDto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PaymentRepositoryCustom {

    Page<TodayDepositDto> searchDescPageTodayDeposit(Pageable pageable, List<Member> members);
}
