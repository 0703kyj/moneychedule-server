package com.money.dto.response.schedule;

import com.money.domain.schedule.dto.ScheduleDto;
import com.money.domain.schedule.entity.Schedule;
import com.money.domain.schedule.entity.ScheduleContent;
import java.util.List;

public record ScheduleResponse(
        Long scheduleId,
        ScheduleDto schedule
) {
    public static ScheduleResponse of(Schedule schedule, List<Long> members) {
        ScheduleContent content = schedule.getContent();
        ScheduleDto scheduleDto = ScheduleDto.builder()
                .labelId(content.getLabel().getId())
                .memo(content.getMemo())
                .startDate(schedule.getEventDate().getStartDate())
                .endDate(schedule.getEventDate().getEndDate())
                .startTime(schedule.getEventDate().getStartTime())
                .endTime(schedule.getEventDate().getEndTime())
                .members(members)
                .repeatType(content.getRepeatType().getValue())
                .build();

        return new ScheduleResponse(schedule.getId(), scheduleDto);
    }
}
