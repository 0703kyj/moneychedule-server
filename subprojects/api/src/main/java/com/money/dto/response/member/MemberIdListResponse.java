package com.money.dto.response.member;

import com.money.domain.member.entity.Member;
import java.util.List;

public record MemberIdListResponse(
        List<MemberIdResponse> members
) {
    public static MemberIdListResponse from(List<Member> members) {
        return new MemberIdListResponse(members.stream().map(MemberIdResponse::from).toList());
    }
}
