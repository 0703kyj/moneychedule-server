package com.money.domain.schedule.entity;

import com.money.domain.DeletableBaseEntity;
import com.money.domain.schedule.dto.ScheduleDto;
import com.money.domain.schedule.entity.enums.RepeatType;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    private String memo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "label_id")
    private Label label;

    @Embedded
    @AttributeOverride(name = "date", column = @Column(name = "start_date"))
    @AttributeOverride(name = "time", column = @Column(name = "start_time"))
    private EventDate startDate;

    @Embedded
    @AttributeOverride(name = "date", column = @Column(name = "end_date"))
    @AttributeOverride(name = "time", column = @Column(name = "end_time"))
    private EventDate endDate;

    @Enumerated(EnumType.STRING)
    private RepeatType repeatType;

    public static Schedule of(Label label, ScheduleDto scheduleDto) {
        return Schedule.builder()
                .label(label)
                .memo(scheduleDto.memo())
                .startDate(EventDate.of(scheduleDto.startDate(),scheduleDto.startTime()))
                .endDate(EventDate.of(scheduleDto.endDate(),scheduleDto.endTime()))
                .repeatType(RepeatType.fromString(scheduleDto.repeatType()))
                .build();
    }

    public Schedule update(Label newLabel, ScheduleDto scheduleDto) {
        this.label = newLabel;
        this.memo = scheduleDto.memo();
        this.startDate.updateDateAndTime(scheduleDto.startDate(), scheduleDto.startTime());
        this.endDate.updateDateAndTime(scheduleDto.endDate(), scheduleDto.endTime());
        this.repeatType = RepeatType.fromString(scheduleDto.repeatType());

        return this;
    }
}
