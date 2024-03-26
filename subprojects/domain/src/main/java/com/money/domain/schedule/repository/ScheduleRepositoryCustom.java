package com.money.domain.schedule.repository;

import com.money.domain.schedule.entity.Schedule;
import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepositoryCustom {

    List<Schedule> getSchedulePerMonth(LocalDate month, Long memberId);
    List<Schedule> getSchedulesPerDay(LocalDate day, Long memberId);
}
