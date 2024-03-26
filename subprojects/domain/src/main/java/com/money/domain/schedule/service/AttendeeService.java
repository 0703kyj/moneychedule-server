package com.money.domain.schedule.service;

import com.money.domain.member.entity.Member;
import com.money.domain.schedule.entity.Attendee;
import com.money.domain.schedule.entity.Schedule;
import com.money.domain.schedule.exception.InvalidAccessScheduleException;
import com.money.domain.schedule.repository.AttendeeRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
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

    public List<Long> findAllAttendeesInSchedule(Schedule schedule) {
        return attendeeRepository.findBySchedule(schedule).stream()
                .map(Attendee::getMemberId)
                .toList();
    }

    private Boolean checkExistMemberInSchedule(Long memberId, Long scheduleId) {
        return attendeeRepository.existsByMemberIdAndScheduleId(memberId, scheduleId);
    }
}
