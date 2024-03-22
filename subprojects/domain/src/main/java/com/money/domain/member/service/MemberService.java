package com.money.domain.member.service;

import com.money.domain.member.entity.Member;
import com.money.domain.member.exception.MemberAlreadyExistException;
import com.money.domain.member.repository.MemberRepository;
import com.money.domain.team.entity.Team;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member saveMember(Member member) {
        Optional<Member> findMember = memberRepository.findByEmailAndPlatform(member.getEmail(),
                member.getPlatform());

        if (findMember.isPresent()) {
            throw new MemberAlreadyExistException();
        }
        return memberRepository.save(member);
    }

    @Transactional
    public void enterTeam(Member member, Team newTeam) {
        Team currentTeam = member.getTeam();
        if (currentTeam != null) {
            currentTeam.subMemberCount();
        }
        member.updateTeam(newTeam);
    }

    public Optional<Long> getTeamIfExist(Member member) {
        if (member.getTeam() != null) {
            return Optional.of(member.getTeam().getId());
        }
        return Optional.empty();
    }
}