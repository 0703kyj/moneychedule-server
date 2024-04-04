package com.money.service.schedule;

import com.money.domain.member.entity.Member;
import com.money.domain.member.service.MemberService;
import com.money.domain.schedule.dto.ScheduleDto;
import com.money.domain.schedule.entity.EventDate;
import com.money.domain.schedule.entity.Label;
import com.money.domain.schedule.entity.Schedule;
import com.money.domain.schedule.entity.ScheduleContent;
import com.money.domain.schedule.entity.enums.RepeatType;
import com.money.domain.schedule.repository.ScheduleContentRepository;
import com.money.domain.schedule.repository.ScheduleRepository;
import com.money.domain.schedule.service.AttendeeService;
import com.money.domain.schedule.service.LabelService;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleRepeatService {

    private final LabelService labelService;
    private final MemberService memberService;
    private final AttendeeService attendeeService;
    private final ScheduleRepository scheduleRepository;
    private final ScheduleContentRepository scheduleContentRepository;

    @Transactional
    public List<Long> saveRepeatSchedule(Long memberId, ScheduleDto scheduleDto) {

        Label findLabel = labelService.findById(scheduleDto.labelId());
        ScheduleContent content = ScheduleContent.builder()
                .label(findLabel)
                .memo(scheduleDto.memo())
                .repeatType(RepeatType.fromString(scheduleDto.repeatType()))
                .build();

        ScheduleContent savedContent = scheduleContentRepository.save(content);

        return repeatSchedule(memberId, scheduleDto, savedContent);
    }

    private List<Long> repeatSchedule(Long memberId,  ScheduleDto scheduleDto,
            ScheduleContent savedContent) {

        List<Schedule> schedules = generateSchedules(scheduleDto, savedContent);

        scheduleRepository.saveAll(schedules);
        Member findMember = memberService.findById(memberId);
        for (Schedule schedule : schedules) {
            addAttendees(scheduleDto.members(),findMember, schedule);
        }

        return schedules.stream().map(Schedule::getId).toList();
    }

    private static List<Schedule> generateSchedules(ScheduleDto scheduleDto,
            ScheduleContent savedContent) {
        RepeatType repeatType = RepeatType.fromString(scheduleDto.repeatType());

        LocalDate startDate = scheduleDto.startDate();
        LocalDate endDate = scheduleDto.endDate();
        LocalTime startTime = scheduleDto.startTime();
        LocalTime endTime = scheduleDto.endTime();

        List<Schedule> schedules = new ArrayList<>();
        for (int i = 0; i < repeatType.getRepeatCount(); i++) {
            EventDate eventDate = EventDate.of(startDate, endDate, startTime, endTime);
            eventDate.plus(repeatType, i);
            Schedule schedule = Schedule.of(savedContent, eventDate);
            schedules.add(schedule);
        }
        return schedules;
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

    private Boolean checkSameTeam(Member attendee, Member findMember) {
        return Objects.equals(attendee.getTeam().getId(), findMember.getTeam().getId());
    }
}
