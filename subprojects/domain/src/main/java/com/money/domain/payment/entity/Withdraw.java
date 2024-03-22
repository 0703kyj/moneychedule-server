package com.money.domain.payment.entity;

import com.money.domain.payment.entity.enums.WithdrawType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@Getter
@DiscriminatorValue("W")
public class Withdraw extends Payment{
    WithdrawType withdrawType;
}
