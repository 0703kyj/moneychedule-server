package com.money.domain.schedule.repository;

import static com.money.domain.schedule.entity.QAttendee.*;
import static com.money.domain.schedule.entity.QSchedule.*;

import com.money.domain.schedule.entity.Schedule;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ScheduleRepositoryCustomImpl implements ScheduleRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Schedule> getSchedulePerMonth(LocalDate month, Long memberId) {
        LocalDate startOfMonth = month.withDayOfMonth(1); // 이 달의 시작
        LocalDate endOfMonth = month.withDayOfMonth(month.lengthOfMonth()); // 이 달의 끝

        return queryFactory.select(schedule)
                .from(attendee)
                .join(attendee.schedule, schedule)
                .where(attendee.member.id.eq(memberId),
                        betweenMonthOfStartDate(startOfMonth, endOfMonth)
                        .or(betweenMonthOfEndDate(startOfMonth, endOfMonth)))
                .fetch();
    }

    private BooleanExpression betweenMonthOfStartDate(LocalDate startOfMonth, LocalDate endOfMonth) {
        return schedule.startDate.date.between(startOfMonth, endOfMonth);
    }

    private BooleanExpression betweenMonthOfEndDate(LocalDate startOfMonth, LocalDate endOfMonth) {
        return schedule.endDate.date.between(startOfMonth, endOfMonth);
    }
}
