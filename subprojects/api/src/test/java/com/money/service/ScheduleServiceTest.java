package com.money.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.money.domain.member.entity.Member;
import com.money.domain.member.entity.enums.Platform;
import com.money.domain.member.repository.MemberRepository;
import com.money.domain.member.service.MemberService;
import com.money.domain.schedule.dto.ScheduleDto;
import com.money.domain.schedule.repository.AttendeeRepository;
import com.money.domain.schedule.repository.ScheduleRepository;
import com.money.domain.schedule.service.ScheduleService;
import com.money.domain.team.entity.Team;
import com.money.domain.team.repository.TeamRepository;
import com.money.domain.team.service.TeamService;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class ScheduleServiceTest {

    @Autowired
    ScheduleService scheduleService;
    @Autowired
    MemberService memberService;
    @Autowired
    TeamService teamService;

    @Autowired
    ScheduleRepository scheduleRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    AttendeeRepository attendeeRepository;

    @Test
    void 일정을_생성시_참가자를_함께_설정할_수_있다() {

        Member member = Member.of("abc@naver.com", "fdsf", Platform.EMAIL);
        Member member2 = Member.of("abc2@naver.com", "fdsf", Platform.EMAIL);
        Member member3 = Member.of("abc3@naver.com", "fdsf", Platform.EMAIL);
        Member member4 = Member.of("abc4@naver.com", "fdsf", Platform.EMAIL);
        memberService.saveMember(member);
        memberService.saveMember(member2);
        memberService.saveMember(member3);
        memberService.saveMember(member4);

        Team newTeam = teamService.createNewTeam();
        memberService.enterTeam(member, newTeam);
        memberService.enterTeam(member2, newTeam);
        memberService.enterTeam(member3, newTeam);

        ScheduleDto scheduleDto = ScheduleDto.builder()
                .memo("memo")
                .labelId(1L)
                .startDate(LocalDate.now())
                .startTime(LocalTime.now())
                .endDate(LocalDate.now())
                .endTime(LocalTime.now())
                .repeatType("매일")
                .members(List.of(member2.getId(), member3.getId(), member4.getId()))
                .build();

        List<Long> schedule = scheduleService.saveSchedule(member.getId(), scheduleDto);

        assertThat(attendeeRepository.existsByMemberIdAndScheduleId(member.getId(),schedule.get(0)))
                .isTrue();
        assertThat(attendeeRepository.existsByMemberIdAndScheduleId(member2.getId(),schedule.get(0)))
                .isTrue();
        assertThat(attendeeRepository.existsByMemberIdAndScheduleId(member3.getId(),schedule.get(0)))
                .isTrue();

    }
}
