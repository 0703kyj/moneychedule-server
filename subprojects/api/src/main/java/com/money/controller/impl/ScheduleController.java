package com.money.controller.impl;

import com.money.controller.ScheduleApi;
import com.money.domain.schedule.dto.ScheduleDto;
import com.money.domain.schedule.entity.Schedule;
import com.money.domain.schedule.service.AttendeeService;
import com.money.domain.schedule.service.ScheduleService;
import com.money.dto.request.schedule.ScheduleRequest;
import com.money.dto.response.schedule.ScheduleIdResponse;
import com.money.dto.response.schedule.ScheduleListResponse;
import com.money.dto.response.schedule.ScheduleResponse;
import com.money.util.LocalDateConverter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ScheduleController implements ScheduleApi {

    private final ScheduleService scheduleService;
    private final AttendeeService attendeeService;

    @Override
    public ResponseEntity<ScheduleIdResponse> saveSchedule(Long memberId, ScheduleRequest request) {
        ScheduleDto scheduleDto = ScheduleDto.builder()
                .labelId(request.labelId())
                .memo(request.memo())
                .startDate(request.startDate())
                .startTime(request.startTime())
                .endDate(request.endDate())
                .endTime(request.endTime())
                .repeatType(request.repeatType())
                .members(request.members())
                .build();

        Schedule schedule = scheduleService.saveSchedule(memberId, scheduleDto);

        ScheduleIdResponse response = ScheduleIdResponse.from(schedule.getId());

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ScheduleListResponse> getSchedulesPerDay(Long memberId, LocalDate day) {

        List<Schedule> schedules = scheduleService.getSchedulePerDay(memberId, day);

        ScheduleListResponse response = getScheduleListResponse(schedules);

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ScheduleListResponse> getSchedulesPerMonth(Long memberId, int year, int month) {

        LocalDate date = LocalDateConverter.getLocalDate(year, month);
        List<Schedule> schedules = scheduleService.getSchedulePerMonth(memberId, date);

        ScheduleListResponse response = getScheduleListResponse(schedules);

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ScheduleResponse> getSchedule(Long memberId, Long scheduleId) {

        Schedule schedule = scheduleService.findById(memberId, scheduleId);
        List<Long> members = attendeeService.findAllAttendeesInSchedule(schedule);

        ScheduleResponse response = ScheduleResponse.of(schedule, members);

        return ResponseEntity.ok(response);
    }

    private ScheduleListResponse getScheduleListResponse(List<Schedule> schedules) {
        List<ScheduleResponse> scheduleResponses = new ArrayList<>();

        for (Schedule schedule : schedules) {
            List<Long> members = attendeeService.findAllAttendeesInSchedule(schedule);
            scheduleResponses.add(ScheduleResponse.of(schedule, members));
        }

        return ScheduleListResponse.from(scheduleResponses);
    }
}
