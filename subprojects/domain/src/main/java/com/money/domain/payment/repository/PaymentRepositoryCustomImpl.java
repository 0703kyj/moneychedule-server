package com.money.domain.payment.repository;

import static com.money.domain.member.entity.QMember.member;
import static com.money.domain.payment.entity.QDeposit.deposit;
import static com.money.domain.payment.entity.QWithdraw.withdraw;

import com.money.domain.payment.dto.TodayDepositDto;
import com.money.domain.payment.dto.TodayWithdrawDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

@RequiredArgsConstructor
@Slf4j
public class PaymentRepositoryCustomImpl implements PaymentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<TodayDepositDto> searchDescPageTodayDeposit(Pageable pageable, Long teamId) {

        List<TodayDepositDto> content = queryFactory
                .select(Projections.constructor(TodayDepositDto.class,
                        deposit.id,
                        deposit.member.id,
                        deposit.memo,
                        deposit.amount,
                        deposit.depositType))
                .from(deposit)
                .join(deposit.member,member)
                .where(member.team.id.eq(teamId),
                        deposit.paymentDate.after(LocalDate.now().atStartOfDay()),
                        deposit.paymentDate.before(LocalDateTime.now()))
                .orderBy(deposit.amount.desc(),
                        deposit.member.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(deposit.count())
                .from(deposit);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    @Override
    public Page<TodayWithdrawDto> searchDescPageTodayWithdraw(Pageable pageable, Long teamId) {
        List<TodayWithdrawDto> content = queryFactory
                .select(Projections.constructor(TodayWithdrawDto.class,
                        withdraw.id,
                        withdraw.member.id,
                        withdraw.memo,
                        withdraw.amount,
                        withdraw.withdrawType))
                .from(withdraw)
                .join(withdraw.member,member)
                .where(member.team.id.eq(teamId),
                        withdraw.paymentDate.after(LocalDate.now().atStartOfDay()),
                        withdraw.paymentDate.before(LocalDateTime.now()))
                .orderBy(withdraw.amount.desc(),
                        withdraw.member.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(withdraw.count())
                .from(withdraw);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    @Override
    public Long getTotalDepositPerMonth(LocalDate month, Long teamId) {
        LocalDateTime startOfMonth = month.withDayOfMonth(1).atStartOfDay(); // 이 달의 시작
        LocalDateTime endOfMonth = month.withDayOfMonth(month.lengthOfMonth()).atTime(23, 59, 59); // 이 달의 끝

        return queryFactory.select(deposit.amount.sum())
                .from(deposit)
                .join(deposit.member, member)
                .where(deposit.paymentDate.after(startOfMonth),
                        deposit.paymentDate.before(endOfMonth))
                .groupBy(member.team.id)
                .having(member.team.id.eq(teamId))
                .fetchOne();
    }

    @Override
    public Long getTotalWithdrawPerMonth(LocalDate month, Long teamId) {
        LocalDateTime startOfMonth = month.withDayOfMonth(1).atStartOfDay(); // 이 달의 시작
        LocalDateTime endOfMonth = month.withDayOfMonth(month.lengthOfMonth()).atTime(23, 59, 59); // 이 달의 끝

        return queryFactory.select(withdraw.amount.sum())
                .from(withdraw)
                .join(withdraw.member, member)
                .where(withdraw.paymentDate.after(startOfMonth),
                        withdraw.paymentDate.before(endOfMonth))
                .groupBy(member.team.id)
                .having(member.team.id.eq(teamId))
                .fetchOne();
    }
}
