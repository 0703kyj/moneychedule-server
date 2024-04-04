package com.money.dto.response.schedule;

import com.money.domain.schedule.dto.ScheduleDto;
import com.money.domain.schedule.entity.Schedule;
import com.money.domain.schedule.entity.ScheduleContent;

public record ScheduleUpdateResponse(
        Long scheduleId,
        ScheduleDto schedule
) {
    public static ScheduleUpdateResponse of(Schedule schedule) {
        ScheduleContent content = schedule.getContent();
        ScheduleDto scheduleDto = ScheduleDto.builder()
                .labelId(content.getLabel().getId())
                .memo(content.getMemo())
                .startDate(schedule.getEventDate().getStartDate())
                .startTime(schedule.getEventDate().getStartTime())
                .repeatType(content.getRepeatType().getValue())
                .build();

        return new ScheduleUpdateResponse(schedule.getId(), scheduleDto);
    }
}
