package com.money.dto.response.schedule;

import com.money.domain.schedule.dto.ScheduleDto;
import com.money.domain.schedule.entity.Schedule;

public record ScheduleUpdateResponse(
        Long scheduleId,
        ScheduleDto schedule
) {
    public static ScheduleUpdateResponse of(Schedule schedule) {
        ScheduleDto scheduleDto = ScheduleDto.builder()
                .labelId(schedule.getLabel().getId())
                .memo(schedule.getMemo())
                .startDate(schedule.getStartDate().getDate())
                .startTime(schedule.getStartDate().getTime())
                .endDate(schedule.getEndDate().getDate())
                .endTime(schedule.getEndDate().getTime())
                .repeatType(schedule.getRepeatType().getValue())
                .build();

        return new ScheduleUpdateResponse(schedule.getId(), scheduleDto);
    }
}
