package com.money.dto.response.member;


import com.money.domain.member.entity.Member;

public record MemberIdResponse(
        Long memberId,
        String name
) {

    public static MemberIdResponse from(Member member) {
        return new MemberIdResponse(member.getId(), member.getName());
    }
}
