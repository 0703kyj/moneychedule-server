package com.money.domain.payment.entity;

import com.money.domain.member.entity.Member;
import com.money.domain.payment.entity.enums.DepositType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("D")
public class Deposit extends Payment{
    @Enumerated(EnumType.STRING)
    DepositType depositType;

    public static Payment of(Member member, String memo, Long amount, String type) {
        Deposit deposit = Deposit.builder()
                .depositType(DepositType.fromString(type))
                .build();

        deposit.initPayment(member, memo, amount);
        return deposit;
    }
}
