package com.money.domain.team.service;

import com.money.domain.team.entity.Team;
import com.money.domain.team.exception.FailGenerateCodeException;
import com.money.domain.team.util.RandomCodeGenerator;
import com.money.util.RedisUtil;
import java.time.Duration;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TeamInviteCodeProviderImpl implements TeamInviteCodeProvider {

    private static final int MAX_REPEAT_COUNT = 100;
    private static final int CODE_EXPIRED_MINUTE = 1;
    private final TeamService teamService;
    private final RedisUtil redisUtil;

    @Override
    public String getInviteCode(Long teamId) {
        Team team = teamService.findById(teamId);

        if (getRedisValueIfTeamSame(teamId, team).isPresent()) {
            return team.getInviteCode();
        }
        String newInviteCode = generateNewCode(teamId);
        team.updateInviteCode(newInviteCode);
        return newInviteCode;
    }

    private String generateNewCode(Long teamId) {
        String inviteCode = RandomCodeGenerator.generateRandomCode();
        String value = Long.toString(teamId);
        for (int i = 0; i < MAX_REPEAT_COUNT; i++) {
            Duration expiredTime = Duration.ofMinutes(CODE_EXPIRED_MINUTE);

            Boolean isSetSuccess = redisUtil.setDataIfAbsent(inviteCode, value, expiredTime);
            if (Boolean.TRUE.equals(isSetSuccess)) {
                return inviteCode;
            }
        }
        throw new FailGenerateCodeException();
    }

    private Optional<String> getRedisValueIfTeamSame(Long teamId, Team team) {
        if (team.getInviteCode() == null) {
            return Optional.empty();
        }
        return redisUtil.getData(team.getInviteCode()).stream()
                .filter(data -> data.equals(Long.toString(teamId)))
                .findFirst();
    }
}
