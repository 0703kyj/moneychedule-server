package com.money.domain.member.service;

import com.money.domain.member.entity.Member;
import com.money.domain.member.exception.MemberAlreadyExistException;
import com.money.domain.member.exception.NotFoundMemberException;
import com.money.domain.member.repository.MemberRepository;
import com.money.domain.team.entity.Team;
import com.money.domain.team.service.TeamService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final TeamService teamService;

    @Transactional
    public Member saveMember(Member member) {
        Optional<Member> findMember = memberRepository.findByEmailAndPlatform(member.getEmail(),
                member.getPlatform());

        if (findMember.isPresent()) {
            throw new MemberAlreadyExistException();
        }

        Team newTeam = teamService.createNewTeam();
        member.updateTeam(newTeam);

        return memberRepository.save(member);
    }

    @Transactional
    public void enterTeam(Member member, Team newTeam) {
        teamService.validateEnterTeam(newTeam);

        Team currentTeam = member.getTeam();
        if (currentTeam != null) {
            currentTeam.subMemberCount();
        }
        member.updateTeam(newTeam);
    }

    @Transactional
    public Team getTeam(Member member) {
        if (member.getTeam() != null) {
            return member.getTeam();
        }
        Team newTeam = teamService.createNewTeam();
        member.updateTeam(newTeam);

        return member.getTeam();
    }

    public Member findById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(NotFoundMemberException::new);
    }

    public void validateMember(Long memberId) {
        if (Boolean.FALSE.equals(memberRepository.existsById(memberId))) {
            throw new NotFoundMemberException();
        }
    }

    public Optional<Long> getTeamIfExist(Member member) {
        if (member.getTeam() != null) {
            return Optional.of(member.getTeam().getId());
        }
        return Optional.empty();
    }

    public List<Member> getMembers(Long memberId) {
        Member findMember = findById(memberId);

        Team team = findMember.getTeam();

        return memberRepository.findByTeam(team);
    }
}
