package com.money.dto.response.member;

public record InviteCodeResponse(
        String inviteCode
) {
    public static InviteCodeResponse from(String inviteCode) {
        return new InviteCodeResponse(inviteCode);
    }
}
