package com.money.dto.response.member;


import com.money.domain.member.entity.Member;

public record MemberResponse(
        Long memberId,
        String name
) {

    public static MemberResponse from(Member member) {
        return new MemberResponse(member.getId(), member.getName());
    }
}
