package com.money.dto.response.schedule;

public record ScheduleResponse(
        Long scheduleId
) {
    public static ScheduleResponse from(Long scheduleId) {
        return new ScheduleResponse(scheduleId);
    }
}
