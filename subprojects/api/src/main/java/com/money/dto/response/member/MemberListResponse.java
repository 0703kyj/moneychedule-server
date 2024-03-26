package com.money.dto.response.member;

import com.money.domain.member.entity.Member;
import java.util.List;

public record MemberListResponse(
        List<MemberResponse> members
) {
    public static MemberListResponse from(List<Member> members) {
        return new MemberListResponse(members.stream().map(MemberResponse::from).toList());
    }
}
