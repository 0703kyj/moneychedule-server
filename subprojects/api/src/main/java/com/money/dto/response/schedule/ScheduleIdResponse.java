package com.money.dto.response.schedule;

public record ScheduleIdResponse(
        Long scheduleId
) {
    public static ScheduleIdResponse from(Long scheduleId) {
        return new ScheduleIdResponse(scheduleId);
    }
}
