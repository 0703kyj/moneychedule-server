package com.money.domain.schedule.entity;

import jakarta.persistence.Column;
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
    @Column(name = "date")
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    public static EventDate of(LocalDate date, LocalTime startTime, LocalTime endTime) {
        return new EventDate(date, startTime, endTime);
    }

    public void updateDateAndTime(LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
