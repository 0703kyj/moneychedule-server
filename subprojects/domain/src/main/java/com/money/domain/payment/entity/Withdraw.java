package com.money.domain.payment.entity;

import com.money.domain.payment.entity.enums.WithdrawType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

@Entity
@Getter
@DiscriminatorValue("W")
public class Withdraw extends Payment{
    @Enumerated(EnumType.STRING)
    WithdrawType withdrawType;
}
