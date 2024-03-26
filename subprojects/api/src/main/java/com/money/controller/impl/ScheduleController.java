package com.money.controller.impl;

import com.money.controller.ScheduleApi;
import com.money.domain.schedule.dto.ScheduleDto;
import com.money.domain.schedule.entity.Schedule;
import com.money.domain.schedule.repository.LabelRepository;
import com.money.domain.schedule.service.ScheduleService;
import com.money.domain.team.repository.TeamRepository;
import com.money.dto.request.schedule.ScheduleRequest;
import com.money.dto.response.schedule.ScheduleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ScheduleController implements ScheduleApi {

    private final ScheduleService scheduleService;
    private final TeamRepository teamRepository;
    private final LabelRepository labelRepository;

    @Override
    public ResponseEntity<ScheduleResponse> saveSchedule(Long memberId, ScheduleRequest request) {
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
        ScheduleResponse response = ScheduleResponse.from(schedule.getId());

        return ResponseEntity.ok(response);
    }
}
