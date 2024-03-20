package com.money.dto.response.auth;


import com.money.domain.member.entity.Member;

public record MemberRegisterResponse(
        Long memberId
) {

    public static MemberRegisterResponse from(Member member) {
        return new MemberRegisterResponse(member.getId());
    }
}
