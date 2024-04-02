package com.money.domain.schedule.service;

import com.money.domain.member.entity.Member;
import com.money.domain.member.service.MemberService;
import com.money.domain.schedule.dto.ScheduleDto;
import com.money.domain.schedule.dto.TotalScheduleDto;
import com.money.domain.schedule.entity.EventDate;
import com.money.domain.schedule.entity.Label;
import com.money.domain.schedule.entity.Schedule;
import com.money.domain.schedule.entity.ScheduleContent;
import com.money.domain.schedule.entity.enums.RepeatType;
import com.money.domain.schedule.exception.NotFoundScheduleException;
import com.money.domain.schedule.repository.ScheduleContentRepository;
import com.money.domain.schedule.repository.ScheduleRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
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

    private final MemberService memberService;
    private final LabelService labelService;
    private final AttendeeService attendeeService;
    private final ScheduleRepository scheduleRepository;
    private final ScheduleContentRepository scheduleContentRepository;

    @Transactional
    public List<Long> saveSchedule(Long memberId, TotalScheduleDto scheduleDto) {

        Member findMember = memberService.findById(memberId);

        Label findLabel = labelService.findById(scheduleDto.labelId());
        ScheduleContent content = ScheduleContent.builder()
                .label(findLabel)
                .memo(scheduleDto.memo())
                .repeatType(RepeatType.fromString(scheduleDto.repeatType()))
                .build();

        ScheduleContent savedContent = scheduleContentRepository.save(content);

        return saveScheduleBetweenDate(scheduleDto, savedContent, findMember);
    }

    @Transactional
    public Schedule updateScheduleContent(Long memberId, Long scheduleId, ScheduleDto scheduleDto) {

        attendeeService.validateAttendee(memberId, scheduleId);

        Schedule findSchedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(NotFoundScheduleException::new);

        ScheduleDto updateScheduleDto = validateScheduleDto(findSchedule, scheduleDto);

        if (scheduleDto.labelId() != null) {
            Label findLabel  = labelService.findById(scheduleDto.labelId());
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

    private List<Long> saveScheduleBetweenDate(TotalScheduleDto totalScheduleDto, ScheduleContent savedContent,
            Member findMember) {
        List<Long> scheduleIds = new ArrayList<>();

        for (LocalDate date = totalScheduleDto.startDate(); date.isBefore(totalScheduleDto.endDate().plusDays(1)); date = date.plusDays(1)){
            LocalTime startTime = LocalTime.MIN;
            LocalTime endTime = LocalTime.of(23,59,59);

            if (date.equals(totalScheduleDto.startDate())){
                startTime = totalScheduleDto.startTime();
            }
            if (date.equals(totalScheduleDto.endDate())){
                endTime = totalScheduleDto.endTime();
            }

            EventDate eventDate = EventDate.of(date, startTime, endTime);
            Schedule schedule = scheduleRepository.save(Schedule.of(savedContent, eventDate));
            addAttendees(totalScheduleDto.members(), findMember, schedule);
            scheduleIds.add(schedule.getId());
        }

        return scheduleIds;
    }

    private Boolean checkSameTeam(Member attendee, Member findMember) {
        return Objects.equals(attendee.getTeam().getId(), findMember.getTeam().getId());
    }

    private ScheduleDto validateScheduleDto(Schedule findSchedule, ScheduleDto scheduleDto) {
        return ScheduleDto.builder()
                .labelId(isBlankContent(findSchedule.getId(), scheduleDto.labelId()))
                .memo(isBlankContent(findSchedule.getContent().getMemo(), scheduleDto.memo()))
                .startDate(isBlankContent(findSchedule.getEventDate().getDate(),scheduleDto.startDate()))
                .startTime(isBlankContent(findSchedule.getEventDate().getStartTime(),scheduleDto.startTime()))
                .endTime(isBlankContent(findSchedule.getEventDate().getEndTime(),scheduleDto.endTime()))
                .repeatType(isBlankContent(findSchedule.getContent().getRepeatType().getValue(),scheduleDto.repeatType()))
                .build();
    }

    private <T>T isBlankContent(T oldContent, T newContent){
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
