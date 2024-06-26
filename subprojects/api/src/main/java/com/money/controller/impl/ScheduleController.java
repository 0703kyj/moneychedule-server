package com.money.controller.impl;

import com.money.controller.ScheduleApi;
import com.money.domain.schedule.dto.ScheduleDto;
import com.money.domain.schedule.entity.Schedule;
import com.money.domain.schedule.service.AttendeeService;
import com.money.domain.schedule.service.ScheduleService;
import com.money.dto.request.schedule.ScheduleAttendeeUpdateRequest;
import com.money.dto.request.schedule.ScheduleRequest;
import com.money.dto.request.schedule.ScheduleContentUpdateRequest;
import com.money.dto.response.schedule.ScheduleIdListResponse;
import com.money.dto.response.schedule.ScheduleIdResponse;
import com.money.dto.response.schedule.ScheduleListResponse;
import com.money.dto.response.schedule.ScheduleResponse;
import com.money.dto.response.schedule.ScheduleUpdateResponse;
import com.money.service.schedule.ScheduleRepeatService;
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
    private final ScheduleRepeatService scheduleRepeatService;
    private final AttendeeService attendeeService;

    @Override
    public ResponseEntity<ScheduleIdListResponse> saveSchedule(Long memberId, ScheduleRequest request) {
        ScheduleDto scheduleDto = getScheduleDto(request);

        List<Long> schedule = scheduleRepeatService.saveRepeatSchedule(memberId, scheduleDto);
        List<ScheduleIdResponse> scheduleIdResponses = schedule.stream().map(ScheduleIdResponse::from).toList();

        ScheduleIdListResponse response = ScheduleIdListResponse.from(scheduleIdResponses);

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
    public ResponseEntity<ScheduleIdResponse> deleteSchedule(Long memberId, Long scheduleId) {
        scheduleService.deleteSchedule(memberId, scheduleId);

        ScheduleIdResponse response = ScheduleIdResponse.from(scheduleId);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ScheduleUpdateResponse> updateScheduleContent(Long memberId, Long scheduleId,
            ScheduleContentUpdateRequest request) {
        ScheduleDto scheduleDto = getScheduleDto(request);

        Schedule schedule = scheduleService.updateScheduleContent(memberId, scheduleId,
                scheduleDto);
        ScheduleUpdateResponse response = ScheduleUpdateResponse.of(schedule);

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ScheduleResponse> updateScheduleAttendee(Long memberId, Long scheduleId,
            ScheduleAttendeeUpdateRequest request) {

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

    private ScheduleDto getScheduleDto(ScheduleContentUpdateRequest request) {
        return ScheduleDto.builder()
                .labelId(request.labelId())
                .memo(request.memo())
                .startDate(request.startDate())
                .startTime(request.startTime())
                .endTime(request.endTime())
                .repeatType(request.repeatType())
                .build();
    }
}
