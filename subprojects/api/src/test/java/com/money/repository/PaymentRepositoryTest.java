package com.money.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.money.config.QuerydslConfig;
import com.money.domain.member.entity.Member;
import com.money.domain.member.entity.enums.Platform;
import com.money.domain.member.repository.MemberRepository;
import com.money.domain.payment.dto.TodayDepositDto;
import com.money.domain.payment.entity.Deposit;
import com.money.domain.payment.entity.Payment;
import com.money.domain.payment.repository.PaymentRepository;
import com.money.domain.team.entity.Team;
import com.money.domain.team.repository.TeamRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(QuerydslConfig.class)
class PaymentRepositoryTest {

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private TeamRepository teamRepository;

    @Test
    void 멤버의_지출_내역을_공유_중인_맴버의_것과_함께_내림차순으로_가져올_수_있다() {
        int PAGE_SIZE = 5;

        Member member1 = memberRepository.save(Member.of("abc@naver.com", "abc", Platform.EMAIL));
        Member member2 = memberRepository.save(Member.of("abc2@naver.com", "abc", Platform.EMAIL));
        Team newTeam = teamRepository.save(new Team());

        member1.updateTeam(newTeam);
        member2.updateTeam(newTeam);
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                Payment deposit = Deposit.of(member1, "1", (long) i, "식비");
                paymentRepository.save(deposit);
            }else {
                Payment deposit2 = Deposit.of(member2, "1", (long) i, "식비");
                paymentRepository.save(deposit2);
            }
        }
        Pageable pageable = PageRequest.of(0, PAGE_SIZE);

        Page<TodayDepositDto> todayDepositDto = paymentRepository.searchDescPageTodayDeposit(
                pageable, newTeam.getId());

        List<TodayDepositDto> content = todayDepositDto.getContent();
        assertThat(content).hasSize(PAGE_SIZE);
        assertThat(todayDepositDto.getTotalElements()).isEqualTo(10);

        for (int i = 0; i < PAGE_SIZE; i++) {
            if (i % 2 == 0) {
                assertThat(content.get(i).memberId()).isEqualTo(member2.getId());
            }else{
                assertThat(content.get(i).memberId()).isEqualTo(member1.getId());
            }
            assertThat(content.get(i).amount()).isEqualTo(9 - i);
        }
    }

    @Test
    void 공유_중인_맴버의_지출도_함께_계산한다() {
        Member member1 = memberRepository.save(Member.of("abc@naver.com", "abc", Platform.EMAIL));
        Member member2 = memberRepository.save(Member.of("abc2@naver.com", "abc", Platform.EMAIL));
        Team newTeam = teamRepository.save(new Team());

        member1.updateTeam(newTeam);
        member2.updateTeam(newTeam);
        int sum = 0;
        for (int i = 0; i < 10; i++) {
            Payment deposit = Deposit.of(member1, "1", (long) i, "식비");
            Payment deposit2 = Deposit.of(member2, "1", (long) i, "식비");
            paymentRepository.save(deposit);
            paymentRepository.save(deposit2);
            sum += 2 * i;
        }

        Long totalDeposit = paymentRepository.getTotalDepositPerMonth(LocalDate.now(),
                member1.getTeam().getId());

        assertThat(totalDeposit).isEqualTo(sum);
    }

    @Test
    void 해당_달에_속해있는_모든_지출의_합을_가져올_수_있다() {
        int MINUS_MONTH = 1;

        Member member = memberRepository.save(Member.of("abc@naver.com", "abc", Platform.EMAIL));
        LocalDateTime date = LocalDateTime.now().minusMonths(MINUS_MONTH);

        int sum = 0;
        for (int i = 0; i < 10; i++) {
            Payment deposit = Deposit.of(member, "1", (long) i, "식비");
            paymentRepository.save(deposit);
            sum += i;
        }

        int sumMinusMonth = 0;
        for (int i = 0; i < 12; i++) {
            Payment depositMinusMonth = Deposit.of(member, "1", (long) i, "식비", date);
            paymentRepository.save(depositMinusMonth);
            sumMinusMonth += i;
        }

        Team newTeam = teamRepository.save(new Team());
        member.updateTeam(newTeam);

        Long totalDeposit = paymentRepository.getTotalDepositPerMonth(LocalDate.now(),
                member.getTeam().getId());
        Long totalDepositMinusMonth = paymentRepository.getTotalDepositPerMonth(
                LocalDate.now().minusMonths(MINUS_MONTH),
                member.getTeam().getId());

        assertThat(totalDeposit).isEqualTo(sum);
        assertThat(totalDepositMinusMonth).isEqualTo(sumMinusMonth);
    }
}

