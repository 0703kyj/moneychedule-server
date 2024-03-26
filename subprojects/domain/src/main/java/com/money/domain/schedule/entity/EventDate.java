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
    @Column(name = "time")
    private LocalTime time;

    public static EventDate of(LocalDate date, LocalTime time) {
        return new EventDate(date, time);
    }
}
