package com.money.domain.schedule.service;

import com.money.domain.member.entity.Member;
import com.money.domain.member.service.MemberService;
import com.money.domain.schedule.dto.ScheduleDto;
import com.money.domain.schedule.entity.Label;
import com.money.domain.schedule.entity.Schedule;
import com.money.domain.schedule.exception.NotFoundScheduleException;
import com.money.domain.schedule.repository.ScheduleRepository;
import java.time.LocalDate;
import java.util.List;
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
    private final AttendeeService attendeeService;
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public Schedule saveSchedule(Long memberId, ScheduleDto scheduleDto) {

        Member findMember = memberService.findById(memberId);

        Label findLabel = labelService.findById(scheduleDto.labelId());
        Schedule schedule = scheduleRepository.save(Schedule.of(findLabel, scheduleDto));

        attendeeService.addAttendee(findMember, schedule);
        for (Long attendeeId : scheduleDto.members()) {
            Member attendee = memberService.findById(attendeeId);
            if (Objects.equals(attendee.getTeam().getId(), findMember.getTeam().getId())) {
                attendeeService.addAttendee(attendee, schedule);
            }
        }
        return schedule;
    }

    public Schedule findById(Long memberId, Long scheduleId) {
        attendeeService.validateAttendee(memberId, scheduleId);

        return scheduleRepository.findById(scheduleId)
                .orElseThrow(NotFoundScheduleException::new);
    }

    public List<Schedule> getSchedulePerMonth(Long memberId, LocalDate date) {
        Member findMember = memberService.findById(memberId);

        return scheduleRepository.getSchedulePerMonth(date,
                findMember.getId());
    }
}
