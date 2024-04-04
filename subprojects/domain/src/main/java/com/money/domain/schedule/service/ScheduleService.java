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
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ScheduleService {

    private final LabelService labelService;
    private final MemberService memberService;
    private final AttendeeService attendeeService;
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public Schedule updateScheduleContent(Long memberId, Long scheduleId, ScheduleDto scheduleDto) {

        attendeeService.validateAttendee(memberId, scheduleId);

        Schedule findSchedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(NotFoundScheduleException::new);

        ScheduleDto updateScheduleDto = validateScheduleDto(findSchedule, scheduleDto);

        if (scheduleDto.labelId() != null) {
            Label findLabel = labelService.findById(scheduleDto.labelId());
            return findSchedule.update(findLabel, updateScheduleDto);
        }
        return findSchedule.update(findSchedule.getContent().getLabel(), updateScheduleDto);
    }

    @Transactional
    public Schedule updateScheduleAttendee(Long memberId, Long scheduleId, List<Long> members) {
        Member findMember = memberService.findById(memberId);
        Schedule findSchedule = findById(memberId, scheduleId);

        members.add(findMember.getId());
        log.info("members: {}", members.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", ")));
        attendeeService.deleteAttendeesNotInMembers(members, findSchedule.getId());

        addAttendees(members, findMember, findSchedule);

        return findSchedule;
    }

    @Transactional
    public void deleteSchedule(Long memberId, Long scheduleId) {
        Schedule findSchedule = findById(memberId, scheduleId);

        //추후 리펙토링 필요
        attendeeService.deleteAttendeesBySchedule(findSchedule);

        scheduleRepository.delete(findSchedule);
    }

    public Schedule findById(Long memberId, Long scheduleId) {
        attendeeService.validateAttendee(memberId, scheduleId);

        return scheduleRepository.findById(scheduleId)
                .orElseThrow(NotFoundScheduleException::new);
    }

    public List<Schedule> getSchedulePerDay(Long memberId, LocalDate day) {
        Member findMember = memberService.findById(memberId);

        return scheduleRepository.getSchedulesPerDay(day, findMember.getId());
    }

    public List<Schedule> getSchedulePerMonth(Long memberId, LocalDate month) {
        Member findMember = memberService.findById(memberId);

        return scheduleRepository.getSchedulePerMonth(month, findMember.getId());
    }

    private Boolean checkSameTeam(Member attendee, Member findMember) {
        return Objects.equals(attendee.getTeam().getId(), findMember.getTeam().getId());
    }

    private ScheduleDto validateScheduleDto(Schedule findSchedule, ScheduleDto scheduleDto) {
        return ScheduleDto.builder()
                .labelId(isBlankContent(findSchedule.getId(), scheduleDto.labelId()))
                .memo(isBlankContent(findSchedule.getContent().getMemo(), scheduleDto.memo()))
                .startDate(isBlankContent(findSchedule.getEventDate().getStartDate(),
                        scheduleDto.startDate()))
                .endDate(isBlankContent(findSchedule.getEventDate().getEndDate(),
                        scheduleDto.endDate()))
                .startTime(isBlankContent(findSchedule.getEventDate().getStartTime(),
                        scheduleDto.startTime()))
                .endTime(isBlankContent(findSchedule.getEventDate().getEndTime(),
                        scheduleDto.endTime()))
                .repeatType(isBlankContent(findSchedule.getContent().getRepeatType().getValue(),
                        scheduleDto.repeatType()))
                .build();
    }

    private <T> T isBlankContent(T oldContent, T newContent) {
        if (newContent == null) {
            return oldContent;
        }
        return newContent;
    }

    private void addAttendees(List<Long> members, Member findMember, Schedule findSchedule) {
        attendeeService.addAttendee(findMember, findSchedule);
        for (Long attendeeId : members) {
            Member attendee = memberService.findById(attendeeId);
            if (Boolean.TRUE.equals(checkSameTeam(attendee, findMember))) {
                attendeeService.addAttendee(attendee, findSchedule);
            }
        }
    }
}
