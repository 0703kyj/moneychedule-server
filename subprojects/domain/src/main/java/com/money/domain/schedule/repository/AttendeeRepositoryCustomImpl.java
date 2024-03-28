package com.money.domain.schedule.repository;

import static com.money.domain.schedule.entity.QAttendee.attendee;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AttendeeRepositoryCustomImpl implements AttendeeRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Long> getDeleteMembersInAttendee(List<Long> members, Long scheduleId) {
        return queryFactory.select(attendee.member.id)
                .from(attendee)
                .where(attendee.schedule.id.eq(scheduleId),
                        attendee.member.id.notIn(members))
                .distinct()
                .fetch();
    }

    @Override
    public void deleteAttendeesByMemberIds(List<Long> members) {
        if (members.isEmpty()) {
            return;
        }
        queryFactory.delete(attendee)
                .where(attendee.member.id.in(members))
                .execute();
    }

    @Override
    public Boolean existsByMemberIdAndScheduleId(Long memberId, Long scheduleId) {

        Long result = queryFactory.select(attendee.id)
                .from(attendee)
                .where(attendee.member.id.eq(memberId),
                        attendee.schedule.id.eq(scheduleId))
                .fetchFirst();

        return result != null;
    }
}
