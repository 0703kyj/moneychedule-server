package com.money.dto.response.schedule;

import java.util.List;

public record ScheduleIdListResponse(
        List<ScheduleIdResponse> schedules

) {
    public static ScheduleIdListResponse from(List<ScheduleIdResponse> scheduleResponses) {
        return new ScheduleIdListResponse(scheduleResponses);
    }
}
