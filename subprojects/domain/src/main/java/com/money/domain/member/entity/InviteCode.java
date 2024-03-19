package com.money.domain.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Size;
import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class InviteCode {

    private static final Long CODE_VALIDITY_IN_MILLISECONDS = 300_000L;

    @Size(min = 5, max = 5)
    @Column(length = 5)
    @EqualsAndHashCode.Include
    private String code;
    private Date expiredTime;

    public static InviteCode from(int code) {
        Date now = new Date();
        Date expiredTime = new Date(now.getTime() + CODE_VALIDITY_IN_MILLISECONDS);

        return InviteCode.builder()
                .code(Integer.toString(code))
                .expiredTime(expiredTime)
                .build();
    }
}
