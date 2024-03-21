package com.money.dto.response.member;

public record SetTeamResponse(
        Long teamId,
        int memberCount
) {
    public static SetTeamResponse of(Long teamId, int memberCount) {
        return new SetTeamResponse(teamId, memberCount);
    }
}
