package com.money.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Locale;
import java.util.Set;
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
    @Builder.Default
    private Long followCount = 0L;
    private Locale locale;
    private boolean activated;

    public static Member of(String email, String password) {
        return Member.builder()
                .name("username")
                .email(email)
                .password(password)
                .phoneNumber("010-1111-1111")
                .birth(LocalDate.now())
                .locale(Locale.KOREA)
                .activated(true)
                .build();
    }
}
