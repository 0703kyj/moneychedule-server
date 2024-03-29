package com.money.domain.schedule.repository;

import com.money.domain.schedule.entity.Attendee;
import com.money.domain.schedule.entity.Schedule;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendeeRepository extends JpaRepository<Attendee,Long>, AttendeeRepositoryCustom {
    List<Attendee> findBySchedule(Schedule schedule);
    void deleteAllBySchedule(Schedule schedule);
}
