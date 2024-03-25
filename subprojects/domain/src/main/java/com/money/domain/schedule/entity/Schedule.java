package com.money.domain.schedule.entity;

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
public class Schedule {

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
}
