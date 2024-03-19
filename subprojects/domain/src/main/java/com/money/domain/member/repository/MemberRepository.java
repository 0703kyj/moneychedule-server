package com.money.domain.member.repository;

import com.money.domain.member.entity.Member;
import com.money.domain.member.util.Platform;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);
    Optional<Member> findByEmailAndPlatform(String email, Platform platform);

    Optional<Member> findByPlatformAndPlatformId(Platform platform, String platformId);

    Boolean existsByEmailAndPlatform(String email, Platform platform);

}
