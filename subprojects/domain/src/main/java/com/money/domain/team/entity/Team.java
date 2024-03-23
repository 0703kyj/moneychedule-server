package com.money.domain.team.entity;

import com.money.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Team extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long id;
    private String inviteCode;
    @Builder.Default
    private int memberCount = 0;

    @Embedded
    private Anniversary anniversary;

    public void subMemberCount() {
        this.memberCount--;
    }

    public void addMemberCount() {
        this.memberCount++;
    }

    public void updateInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }
}
