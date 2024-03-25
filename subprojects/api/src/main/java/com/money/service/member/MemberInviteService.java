package com.money.service.member;

import com.money.domain.member.entity.Member;
import com.money.domain.member.exception.NotFoundMemberException;
import com.money.domain.member.repository.MemberRepository;
import com.money.domain.member.service.MemberService;
import com.money.domain.team.entity.Team;
import com.money.domain.team.service.TeamInviteCodeProvider;
import com.money.domain.team.service.TeamService;
import com.money.dto.response.member.InviteCodeResponse;
import com.money.dto.response.member.SetTeamResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberInviteService {

    private final TeamInviteCodeProvider teamInviteCodeProvider;
    private final MemberService memberService;
    private final TeamService teamService;

    @Transactional
    public InviteCodeResponse getInviteCode(Long memberId) {
        Member findMember = memberService.findById(memberId);

        Optional<Long> teamId = memberService.getTeamIfExist(findMember);
        if (teamId.isEmpty()) {
            Team newTeam = teamService.createNewTeam();
            findMember.updateTeam(newTeam);
        }

        String inviteCode = teamInviteCodeProvider.getInviteCode(findMember.getTeam().getId());
        return InviteCodeResponse.from(inviteCode);
    }

    @Transactional
    public SetTeamResponse setTeam(Long memberId, String inviteCode) {
        Member findMember = memberService.findById(memberId);

        Team findTeam = teamService.findByInviteCode(inviteCode);
        teamService.validateEnterTeam(findTeam);

        memberService.enterTeam(findMember, findTeam);
        return SetTeamResponse.of(findTeam.getId(), findTeam.getMemberCount());
    }
}
