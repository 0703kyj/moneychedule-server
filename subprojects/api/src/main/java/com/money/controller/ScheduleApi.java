package com.money.controller;

import com.money.config.auth.MemberId;
import com.money.dto.request.schedule.ScheduleAttendeeRequest;
import com.money.dto.request.schedule.ScheduleRequest;
import com.money.dto.request.schedule.ScheduleUpdateRequest;
import com.money.dto.response.schedule.ScheduleIdResponse;
import com.money.dto.response.schedule.ScheduleListResponse;
import com.money.dto.response.schedule.ScheduleResponse;
import com.money.dto.response.schedule.ScheduleUpdateResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping(value = "/day", params = {"date"})
    ResponseEntity<ScheduleListResponse> getSchedulesPerDay(
            @MemberId Long memberId,

            @DateTimeFormat(iso = ISO.DATE)
            @Parameter(description = "조회할 날짜", example = "yyyy-MM-dd")
            @RequestParam("date")
            LocalDate date
    );

    @GetMapping(value = "/month", params = {"year", "month"})
    ResponseEntity<ScheduleListResponse> getSchedulesPerMonth(
            @MemberId Long memberId,

            @RequestParam("year")
            @Parameter(description = "조회할 년", example = "2024")
            int year,

            @RequestParam("month")
            @Parameter(description = "조회할 월", example = "3")
            int month
    );

    @PutMapping("/content/{id}")
    ResponseEntity<ScheduleUpdateResponse> updateScheduleContent(
            @MemberId Long memberId,
            @PathVariable("id") Long scheduleId,
            @RequestBody ScheduleUpdateRequest request
    );

    @PutMapping("/attendee/{id}")
    ResponseEntity<ScheduleResponse> updateScheduleAttendee(
            @MemberId Long memberId,
            @PathVariable("id") Long scheduleId,
            @RequestBody ScheduleAttendeeRequest request
    );
}
