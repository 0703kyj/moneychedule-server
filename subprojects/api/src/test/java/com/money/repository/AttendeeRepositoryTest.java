package com.money.repository;


import static org.assertj.core.api.Assertions.assertThat;

import com.money.config.QuerydslConfig;
import com.money.domain.member.entity.Member;
import com.money.domain.member.entity.enums.Platform;
import com.money.domain.member.repository.MemberRepository;
import com.money.domain.schedule.dto.ScheduleDto;
import com.money.domain.schedule.entity.Attendee;
import com.money.domain.schedule.entity.Label;
import com.money.domain.schedule.entity.Schedule;
import com.money.domain.schedule.exception.NotFoundLabelException;
import com.money.domain.schedule.repository.AttendeeRepository;
import com.money.domain.schedule.repository.LabelRepository;
import com.money.domain.schedule.repository.ScheduleRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(QuerydslConfig.class)
class AttendeeRepositoryTest {


    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private LabelRepository labelRepository;
    @Autowired
    private AttendeeRepository attendeeRepository;

    @Test
    void 사용자id와_스케쥴id로_참석자를_찾을_수_있다() {

        Member member = memberRepository.save(Member.of("abc@naver.com", "adf", Platform.EMAIL));
        Label label = labelRepository.save(Label.of(member.getTeam(), "fd", "df"));

        ScheduleDto scheduleDto = ScheduleDto.builder()
                .memo("memo")
                .labelId(1L)
                .startDate(LocalDate.now())
                .startTime(LocalTime.now())
                .endDate(LocalDate.now())
                .endTime(LocalTime.now())
                .repeatType("매일")
                .members(List.of())
                .build();

        Schedule schedule = scheduleRepository.save(Schedule.of(label,scheduleDto));
        attendeeRepository.save(Attendee.of(member, schedule));

        assertThat(attendeeRepository.existsByMemberIdAndScheduleId(member.getId(),
                schedule.getId())).isTrue();
    }
}
