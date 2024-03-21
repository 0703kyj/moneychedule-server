package com.money.domain.team.entity;

import com.money.domain.BaseEntity;
import com.money.domain.member.entity.Member;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
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

    @Builder.Default
    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Member> members = new ArrayList<>();

    @Embedded
    private Anniversary anniversary;

    public void updateMemberCount() {
        this.memberCount = this.members.size();
    }

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
