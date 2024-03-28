package com.money.domain.schedule.service;

import com.money.domain.member.entity.Member;
import com.money.domain.schedule.entity.Attendee;
import com.money.domain.schedule.entity.Schedule;
import com.money.domain.schedule.exception.InvalidAccessScheduleException;
import com.money.domain.schedule.repository.AttendeeRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class AttendeeService {

    private final AttendeeRepository attendeeRepository;

    public void validateAttendee(Long memberId, Long scheduleId) {
        if(Boolean.FALSE.equals(checkExistMemberInSchedule(memberId, scheduleId))){
            throw new InvalidAccessScheduleException();
        }
    }

    @Transactional
    public void addAttendee(Member findMember, Schedule schedule) {
        if (Boolean.TRUE.equals(checkExistMemberInSchedule(findMember.getId(), schedule.getId()))) {
            return;
        }
        Attendee attendee = Attendee.of(findMember, schedule);
        attendeeRepository.save(attendee);
    }

    @Transactional
    public void deleteAttendeesNotInMembers(List<Long> members, Long scheduleId) {
        List<Long> deleteMembersInAttendee = attendeeRepository.getDeleteMembersInAttendee(members, scheduleId);

        log.info("deleteMembersInAttendee: {}", members.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", ")));
        attendeeRepository.deleteAttendeesByMemberIds(deleteMembersInAttendee);
    }

    public List<Long> findAllMemberIdInSchedule(Schedule schedule) {
        return attendeeRepository.findBySchedule(schedule).stream()
                .map(Attendee::getMemberId)
                .toList();
    }

    private Boolean checkExistMemberInSchedule(Long memberId, Long scheduleId) {
        return attendeeRepository.existsByMemberIdAndScheduleId(memberId, scheduleId);
    }
}
