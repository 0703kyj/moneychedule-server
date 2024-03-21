package com.money.service.member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.money.domain.member.entity.Member;
import com.money.domain.member.service.MemberService;
import com.money.domain.member.util.Platform;
import com.money.domain.team.exception.OverflowMemberCountException;
import com.money.dto.response.member.InviteCodeResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class MemberInviteServiceTest {

    @Autowired
    private MemberInviteService memberInviteService;
    @Autowired
    private MemberService memberService;

    @Test
    void 사용자는_초대_코드를_생성할_수_있다() {
        //TODO
        Member member = Member.of("abc@naver.com", "adf", Platform.EMAIL);
        memberService.saveMember(member);

        InviteCodeResponse inviteCode = memberInviteService.getInviteCode(member.getId());
        assertThat(inviteCode.inviteCode()).isNotNull();
    }

    @Test
    void 팀원은_2명이상_초대될_수_없다() {
        //TODO
        Member member1 = Member.of("abc@naver.com", "adf", Platform.EMAIL);
        Member member2 = Member.of("abc2@naver.com", "adf", Platform.EMAIL);
        Member member3 = Member.of("abc3@naver.com", "adf", Platform.EMAIL);
        memberService.saveMember(member1);
        memberService.saveMember(member2);
        memberService.saveMember(member3);

        String inviteCode = memberInviteService.getInviteCode(member1.getId()).inviteCode();
        memberInviteService.setTeam(member2.getId(), inviteCode);

        Long member3Id = member3.getId();
        assertThatThrownBy(() -> memberInviteService.setTeam(member3Id, inviteCode))
                .isInstanceOf(OverflowMemberCountException.class);
    }

    @Test
    void 팀원이_팀을_바꿀_경우_다른_팀원을_받을_수_있다() {
        //TODO
        Member member1 = Member.of("abc@naver.com", "adf", Platform.EMAIL);
        Member member2 = Member.of("abc2@naver.com", "adf", Platform.EMAIL);
        Member member3 = Member.of("abc3@naver.com", "adf", Platform.EMAIL);
        memberService.saveMember(member1);
        memberService.saveMember(member2);
        memberService.saveMember(member3);

        String team1 = memberInviteService.getInviteCode(member1.getId()).inviteCode();
        memberInviteService.setTeam(member2.getId(), team1);
        String team2 = memberInviteService.getInviteCode(member3.getId()).inviteCode();
        memberInviteService.setTeam(member2.getId(), team2);
        memberInviteService.setTeam(member3.getId(), team1);

        assertThat(member1.getTeam().getMemberCount()).isEqualTo(2);
        assertThat(member2.getTeam().getMemberCount()).isEqualTo(1);
        assertThat(member3.getTeam().getMemberCount()).isEqualTo(2);
    }
}
