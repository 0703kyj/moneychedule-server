package com.money.controller;

import com.money.config.auth.MemberId;
import com.money.dto.request.schedule.ScheduleRequest;
import com.money.dto.response.schedule.ScheduleIdResponse;
import com.money.dto.response.schedule.ScheduleResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "일정 API", description = "일정 관련 API")
@RequestMapping("/api/v1/schedule")
@SecurityRequirement(name = "JWT")
public interface ScheduleApi {

    @PostMapping
    ResponseEntity<ScheduleIdResponse> saveSchedule(
            @MemberId Long memberId,
            @RequestBody ScheduleRequest request
    );

    @GetMapping("/{id}")
    ResponseEntity<ScheduleResponse> getSchedule(
            @MemberId Long memberId,
            @PathVariable("id") Long scheduleId
    );
}
