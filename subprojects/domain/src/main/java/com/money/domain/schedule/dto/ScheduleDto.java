package com.money.domain.schedule.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.Builder;

@Builder
public record ScheduleDto(
        Long labelId,
        String memo,
        LocalDate startDate,
        LocalTime startTime,
        LocalDate endDate,
        LocalTime endTime,
        String repeatType,
        List<Long> members
) {

}
