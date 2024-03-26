package com.money.dto.response.schedule;

import com.money.domain.schedule.dto.ScheduleDto;
import com.money.domain.schedule.entity.Schedule;
import java.util.List;

public record ScheduleResponse(
        Long scheduleId,
        ScheduleDto schedule
) {
    public static ScheduleResponse of(Schedule schedule, List<Long> members) {
        ScheduleDto scheduleDto = ScheduleDto.builder()
                .labelId(schedule.getLabel().getId())
                .memo(schedule.getMemo())
                .startDate(schedule.getStartDate().getDate())
                .startTime(schedule.getStartDate().getTime())
                .endDate(schedule.getEndDate().getDate())
                .endTime(schedule.getEndDate().getTime())
                .members(members)
                .repeatType(schedule.getRepeatType().getValue())
                .build();

        return new ScheduleResponse(schedule.getId(), scheduleDto);
    }
}
