package com.money.controller.impl;

import com.money.controller.ScheduleApi;
import com.money.domain.schedule.dto.ScheduleDto;
import com.money.domain.schedule.entity.Attendee;
import com.money.domain.schedule.entity.Schedule;
import com.money.domain.schedule.repository.AttendeeRepository;
import com.money.domain.schedule.service.ScheduleService;
import com.money.dto.request.schedule.ScheduleRequest;
import com.money.dto.response.schedule.ScheduleIdResponse;
import com.money.dto.response.schedule.ScheduleResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ScheduleController implements ScheduleApi {

    private final ScheduleService scheduleService;
    private final AttendeeRepository attendeeRepository;

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
    public ResponseEntity<ScheduleResponse> getSchedule(Long memberId, Long scheduleId) {

        Schedule schedule = scheduleService.findById(memberId, scheduleId);

        List<Long> members = attendeeRepository.findBySchedule(schedule).stream()
                .map(Attendee::getMemberId)
                .toList();

        ScheduleResponse response = ScheduleResponse.of(schedule, members);
        return ResponseEntity.ok(response);
    }
}
