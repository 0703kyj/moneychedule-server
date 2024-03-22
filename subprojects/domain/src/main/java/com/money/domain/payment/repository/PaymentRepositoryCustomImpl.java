package com.money.domain.payment.repository;

import static com.money.domain.payment.entity.QDeposit.deposit;

import com.money.domain.member.entity.Member;
import com.money.domain.payment.dto.TodayDepositDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

@RequiredArgsConstructor
public class PaymentRepositoryCustomImpl implements PaymentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<TodayDepositDto> searchDescPageTodayDeposit(Pageable pageable, List<Member> members) {
        List<TodayDepositDto> content = queryFactory
                .select(Projections.constructor(TodayDepositDto.class,
                        deposit.id,
                        deposit.member.id,
                        deposit.memo,
                        deposit.amount,
                        deposit.depositType))
                .from(deposit)
                .where(deposit.member.in(members),
                        deposit.paymentDate.after(LocalDate.now().atStartOfDay()),
                        deposit.paymentDate.before(LocalDateTime.now()))
                .orderBy(deposit.amount.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(deposit.count())
                .from(deposit);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }
}
