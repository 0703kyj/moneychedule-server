package com.money.dto.response.schedule;

import java.util.List;

public record ScheduleListResponse(
        List<ScheduleResponse> schedules
) {
    public static ScheduleListResponse from(List<ScheduleResponse> scheduleResponses) {
        return new ScheduleListResponse(scheduleResponses);
    }
}
