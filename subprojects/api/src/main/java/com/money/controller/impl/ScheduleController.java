package com.money.controller.impl;

import com.money.controller.ScheduleApi;
import com.money.domain.schedule.dto.ScheduleDto;
import com.money.domain.schedule.entity.Schedule;
import com.money.domain.schedule.service.AttendeeService;
import com.money.domain.schedule.service.ScheduleService;
import com.money.dto.request.schedule.ScheduleAttendeeRequest;
import com.money.dto.request.schedule.ScheduleRequest;
import com.money.dto.request.schedule.ScheduleUpdateRequest;
import com.money.dto.response.schedule.ScheduleIdResponse;
import com.money.dto.response.schedule.ScheduleListResponse;
import com.money.dto.response.schedule.ScheduleResponse;
import com.money.dto.response.schedule.ScheduleUpdateResponse;
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
        ScheduleDto scheduleDto = getScheduleDto(request);

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
        List<Long> members = attendeeService.findAllMemberIdInSchedule(schedule);

        ScheduleResponse response = ScheduleResponse.of(schedule, members);

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ScheduleUpdateResponse> updateScheduleContent(Long memberId, Long scheduleId,
            ScheduleUpdateRequest request) {
        ScheduleDto scheduleDto = getScheduleDto(request);

        Schedule schedule = scheduleService.updateScheduleContent(memberId, scheduleId,
                scheduleDto);
        ScheduleUpdateResponse response = ScheduleUpdateResponse.of(schedule);

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ScheduleResponse> updateScheduleAttendee(Long memberId, Long scheduleId,
            ScheduleAttendeeRequest request) {

        Schedule schedule = scheduleService.updateScheduleAttendee(memberId, scheduleId,
                request.members());
        List<Long> members = attendeeService.findAllMemberIdInSchedule(schedule);

        return ResponseEntity.ok(ScheduleResponse.of(schedule, members));
    }

    private ScheduleListResponse getScheduleListResponse(List<Schedule> schedules) {
        List<ScheduleResponse> scheduleResponses = new ArrayList<>();

        for (Schedule schedule : schedules) {
            List<Long> members = attendeeService.findAllMemberIdInSchedule(schedule);
            scheduleResponses.add(ScheduleResponse.of(schedule, members));
        }

        return ScheduleListResponse.from(scheduleResponses);
    }

    private ScheduleDto getScheduleDto(ScheduleRequest request) {
        return ScheduleDto.builder()
                .labelId(request.labelId())
                .memo(request.memo())
                .startDate(request.startDate())
                .startTime(request.startTime())
                .endDate(request.endDate())
                .endTime(request.endTime())
                .repeatType(request.repeatType())
                .members(request.members())
                .build();
    }

    private ScheduleDto getScheduleDto(ScheduleUpdateRequest request) {
        return ScheduleDto.builder()
                .labelId(request.labelId())
                .memo(request.memo())
                .startDate(request.startDate())
                .startTime(request.startTime())
                .endDate(request.endDate())
                .endTime(request.endTime())
                .repeatType(request.repeatType())
                .build();
    }
}
