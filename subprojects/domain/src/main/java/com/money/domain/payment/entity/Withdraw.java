package com.money.domain.payment.entity;

import com.money.domain.member.entity.Member;
import com.money.domain.payment.entity.enums.DepositType;
import com.money.domain.payment.entity.enums.WithdrawType;
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
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("W")
public class Withdraw extends Payment{
    @Enumerated(EnumType.STRING)
    WithdrawType withdrawType;

    public static Payment of(Member member, String memo, Long amount, String type) {
        Withdraw withdraw = Withdraw.builder()
                .withdrawType(WithdrawType.fromString(type))
                .build();

        withdraw.initPayment(member, memo, amount);
        return withdraw;
    }

    public static Payment of(Member member, String memo, Long amount, String type, LocalDateTime date) {
        Withdraw withdraw = Withdraw.builder()
                .withdrawType(WithdrawType.fromString(type))
                .build();

        withdraw.initPayment(member, memo, amount, date);
        return withdraw;
    }
}
