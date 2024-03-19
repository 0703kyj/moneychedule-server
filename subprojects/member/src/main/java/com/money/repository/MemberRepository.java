package com.money.repository;

import com.money.domain.Member;
import com.money.util.Platform;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);
    Optional<Member> findByEmailAndPlatform(String email, Platform platform);

    Optional<Member> findByPlatformAndPlatformId(Platform platform, String platformId);

    Optional<Member> findByInvitedCode(String code);

    Boolean existsByEmailAndPlatform(String email, Platform platform);

}
