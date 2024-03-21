package com.money.domain.member.entity;

import com.money.domain.DeletableBaseEntity;
import com.money.domain.team.entity.Team;
import com.money.domain.member.util.Platform;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDate;
import java.util.Locale;
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
public class Member extends DeletableBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @Enumerated(EnumType.STRING)
    private Platform platform;
    private String platformId;

    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private LocalDate birth;
    private Locale locale;
    private boolean activated;

    public static Member of(String email, String password, Platform platform) {
        return Member.builder()
                .name("username")
                .email(email)
                .password(password)
                .phoneNumber("010-1111-1111")
                .birth(LocalDate.now())
                .locale(Locale.KOREA)
                .platform(platform)
                .activated(true)
                .build();
    }

    public static Member of(String email, Platform platform, String platformId, Locale locale) {
        return Member.builder()
                .email(email)
                .birth(LocalDate.now())
                .locale(locale)
                .platform(platform)
                .platformId(platformId)
                .activated(false)
                .build();
    }

    public void registerSocialMember(String name, String phoneNumber, LocalDate birth){
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.birth = birth;
        this.activated = true;
    }

    public void updateTeam(Team newTeam) {
        this.team = newTeam;
        newTeam.getMembers().add(this);
        this.team.addMemberCount();
    }
}
