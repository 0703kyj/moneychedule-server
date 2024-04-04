package com.money.domain.schedule.entity;

import com.money.domain.schedule.dto.ScheduleDto;
import com.money.domain.schedule.entity.enums.RepeatType;
import jakarta.persistence.Embeddable;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class EventDate {

    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;

    public static EventDate of(LocalDate startDate, LocalDate endDate, LocalTime startTime,
            LocalTime endTime) {
        return new EventDate(startDate, endDate, startTime, endTime);
    }

    public static EventDate from(ScheduleDto scheduleDto) {
        return EventDate.builder()
                .startDate(scheduleDto.startDate())
                .endDate(scheduleDto.endDate())
                .startTime(scheduleDto.startTime())
                .endTime(scheduleDto.endTime())
                .build();
    }

    public void updateDateAndTime(LocalDate startDate, LocalDate endDate, LocalTime startTime,
            LocalTime endTime) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public void plus(RepeatType repeatType, int count) {
        if (repeatType == RepeatType.NONE) {
            return;
        }

        if (repeatType == RepeatType.DAY) {
            plusDay(count);
        } else if (repeatType == RepeatType.WEEK) {
            plusWeek(count);
        } else if (repeatType == RepeatType.MONTH) {
            plusMonth(count);
        } else if (repeatType == RepeatType.YEAR) {
            plusYear(count);
        }
    }

    private void plusYear(int count) {
        this.startDate = getStartDate().plusYears(count);
        this.endDate = getEndDate().plusYears(count);
    }

    private void plusMonth(int count) {
        this.startDate = getStartDate().plusMonths(count);
        this.endDate = getEndDate().plusMonths(count);
    }

    private void plusWeek(int count) {
        this.startDate = getStartDate().plusWeeks(count);
        this.endDate = getEndDate().plusWeeks(count);
    }

    private void plusDay(int count) {
        this.startDate = getStartDate().plusDays(count);
        this.endDate = getEndDate().plusDays(count);
    }
}
