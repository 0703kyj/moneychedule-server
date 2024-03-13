package com.money.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDate;
import java.util.Locale;
import lombok.Getter;

@Entity
@Getter
@Table(name = "member", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"email", "phoneNumber"})
})
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private LocalDate birth;
    private Long followCount;
    private Locale locale;
}
