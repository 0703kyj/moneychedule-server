package com.money.domain.member.repository;

import com.money.domain.member.entity.Member;
import com.money.domain.member.entity.enums.Platform;
import com.money.domain.team.entity.Team;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);
    Optional<Member> findByEmailAndPlatform(String email, Platform platform);
    Optional<Member> findByPlatformAndPlatformId(Platform platform, String platformId);
    List<Member> findByTeam(Team team);
    Boolean existsByEmailAndPlatform(String email, Platform platform);
}
