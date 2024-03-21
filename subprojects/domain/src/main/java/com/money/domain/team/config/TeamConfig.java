package com.money.domain.team.config;

import com.money.domain.team.service.TeamInviteCodeProvider;
import com.money.domain.team.service.TeamInviteCodeProviderImpl;
import com.money.domain.team.service.TeamService;
import com.money.util.RedisUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TeamConfig {

    @Bean
    public TeamInviteCodeProvider teamInviteCodeProvider(
            TeamService teamService,
            RedisUtil redisUtil
    ) {
        return new TeamInviteCodeProviderImpl(teamService, redisUtil);
    }
}
