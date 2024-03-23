package com.money.domain.payment.entity;

import com.money.domain.BaseEntity;
import com.money.domain.member.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Payment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long id;
    private LocalDateTime paymentDate;
    private String memo;
    private Long amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    protected void initPayment(Member member, String memo, Long amount) {
        this.member = member;
        this.memo = memo;
        this.amount = amount;
        this.paymentDate = LocalDateTime.now();
    }
    protected void initPayment(Member member, String memo, Long amount, LocalDateTime date) {
        this.member = member;
        this.memo = memo;
        this.amount = amount;
        this.paymentDate = date;
    }
}
