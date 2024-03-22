package com.money.domain.payment.entity;

import com.money.domain.payment.entity.enums.DepositType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@Getter
@DiscriminatorValue("D")
public class Deposit extends Payment{
    DepositType depositType;
}
