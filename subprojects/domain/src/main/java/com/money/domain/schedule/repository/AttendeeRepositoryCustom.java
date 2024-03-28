package com.money.domain.schedule.repository;

import java.util.List;

public interface AttendeeRepositoryCustom {

    List<Long> getDeleteMembersInAttendee(List<Long> members, Long scheduleId);

    void deleteAttendeesByMemberIds(List<Long> members);
    Boolean existsByMemberIdAndScheduleId(Long memberId, Long scheduleId);
}
