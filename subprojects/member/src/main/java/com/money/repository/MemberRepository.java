package com.money.repository;

import com.money.domain.Member;
import com.money.domain.Platform;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmailAndPlatform(String email, Platform platform);

    Optional<Member> findByEmail(String email);

    Boolean existsByEmailAndPlatform(String email, Platform platform);

    Boolean existsByPlatformAndPlatformId(Platform platform, String platformId);
}
