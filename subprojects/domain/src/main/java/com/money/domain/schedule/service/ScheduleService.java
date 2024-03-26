package com.money.domain.schedule.service;

import com.money.domain.member.entity.Member;
import com.money.domain.member.service.MemberService;
import com.money.domain.schedule.dto.ScheduleDto;
import com.money.domain.schedule.entity.Attendee;
import com.money.domain.schedule.entity.Label;
import com.money.domain.schedule.entity.Schedule;
import com.money.domain.schedule.repository.AttendeeRepository;
import com.money.domain.schedule.repository.ScheduleRepository;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleService {

    private final MemberService memberService;
    private final LabelService labelService;
    private final ScheduleRepository scheduleRepository;
    private final AttendeeRepository attendeeRepository;

    @Transactional
    public Schedule saveSchedule(Long memberId, ScheduleDto scheduleDto) {

        Member findMember = memberService.findById(memberId);

        Label findLabel = labelService.findById(scheduleDto.labelId());
        Schedule schedule = Schedule.of(findLabel, scheduleDto);

        addAttendee(findMember, schedule);
        for (Long attendeeId : scheduleDto.members()) {
            Member attendee = memberService.findById(attendeeId);
            if (Objects.equals(attendee.getTeam().getId(), findMember.getTeam().getId())) {
                addAttendee(attendee, schedule);
            }
        }
        return scheduleRepository.save(schedule);
    }

    private void addAttendee(Member findMember, Schedule schedule) {
        Attendee attendee = Attendee.of(findMember, schedule);
        attendeeRepository.save(attendee);
    }
}
