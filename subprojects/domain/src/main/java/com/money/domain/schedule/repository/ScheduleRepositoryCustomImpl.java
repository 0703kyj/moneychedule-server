package com.money.domain.schedule.repository;

import static com.money.domain.schedule.entity.QAttendee.*;
import static com.money.domain.schedule.entity.QSchedule.*;
import static com.money.domain.schedule.entity.QScheduleContent.scheduleContent;

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
                .join(schedule.content,scheduleContent).fetchJoin()
                .where(attendee.member.id.eq(memberId),
                        betweenMonthOfStartDate(startOfMonth, endOfMonth)
                                .or(betweenMonthOfEndDate(startOfMonth, endOfMonth)))
                .fetch();
    }

    @Override
    public List<Schedule> getSchedulesPerDay(LocalDate day, Long memberId) {

        return queryFactory.select(schedule)
                .from(attendee)
                .join(attendee.schedule, schedule)
                .join(schedule.content,scheduleContent).fetchJoin()
                .where(attendee.member.id.eq(memberId),
                        schedule.eventDate.startDate.loe(day)
                                .and(schedule.eventDate.endDate.goe(day)))
                .fetch();
    }

    private BooleanExpression betweenMonthOfStartDate(LocalDate startOfMonth, LocalDate endOfMonth) {
        return schedule.eventDate.startDate.between(startOfMonth, endOfMonth);
    }

    private BooleanExpression betweenMonthOfEndDate(LocalDate startOfMonth, LocalDate endOfMonth) {
        return schedule.eventDate.endDate.between(startOfMonth, endOfMonth);
    }
}
