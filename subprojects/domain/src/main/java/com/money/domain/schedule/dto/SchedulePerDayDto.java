package com.money.domain.schedule.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.Builder;

@Builder
public record SchedulePerDayDto(
        LocalDate eventDate,
        LocalTime startTime,
        LocalTime endTime,
        List<Long> members
) {

    public static SchedulePerDayDto of(LocalDate eventDate, LocalTime startTime, LocalTime endTime,
            List<Long> members) {
        return new SchedulePerDayDto(eventDate, startTime, endTime, members);
    }
}
