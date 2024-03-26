package com.money.dto.response.member;

import com.money.domain.member.entity.Member;
import java.util.List;

public record MemberListResponse(
        List<MemberIdResponse> members
) {
    public static MemberListResponse from(List<Member> members) {
        return new MemberListResponse(members.stream().map(MemberIdResponse::from).toList());
    }
}
