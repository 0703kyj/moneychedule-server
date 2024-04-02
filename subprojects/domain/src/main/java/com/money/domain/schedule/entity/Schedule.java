package com.money.domain.schedule.entity;

import com.money.domain.DeletableBaseEntity;
import com.money.domain.schedule.dto.ScheduleDto;
import com.money.domain.schedule.entity.enums.RepeatType;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule extends DeletableBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long id;

    @Embedded
    private EventDate eventDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_content_id")
    ScheduleContent content;

    public static Schedule of(ScheduleContent content, EventDate eventDate) {

        return Schedule.builder()
                .eventDate(eventDate)
                .content(content)
                .build();
    }

    public Schedule update(Label newLabel, ScheduleDto scheduleDto) {
        this.content.update(newLabel,scheduleDto.memo(),RepeatType.fromString(scheduleDto.repeatType()));
        this.eventDate.updateDateAndTime(scheduleDto.startDate(), scheduleDto.startTime(), scheduleDto.endTime());

        return this;
    }
}
