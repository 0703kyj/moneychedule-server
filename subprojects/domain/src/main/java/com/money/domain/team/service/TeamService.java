package com.money.domain.team.service;

import com.money.domain.member.entity.Member;
import com.money.domain.team.entity.Team;
import com.money.domain.team.exception.NotFoundTeamException;
import com.money.domain.team.exception.OverflowMemberCountException;
import com.money.domain.team.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeamService {

    private static final int MAX_ENTER_TEAM_COUNT = 2;

    private final TeamRepository teamRepository;

    @Transactional
    public Team createNewTeam() {
        return teamRepository.save(new Team());
    }

    public Team findById(Long teamId) {
        return teamRepository.findById(teamId)
                .orElseThrow(NotFoundTeamException::new);
    }

    public Team findByInviteCode(String inviteCode) {
        return teamRepository.findByInviteCode(inviteCode)
                .orElseThrow(NotFoundTeamException::new);
    }

    public void validateEnterTeam(Team team) {
        if (team.getMemberCount() >= MAX_ENTER_TEAM_COUNT) {
            throw new OverflowMemberCountException();
        }
    }
}
