package com.money.domain.group.repository;

import com.money.domain.group.entity.Group;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {
    Optional<Group> findByInviteCode(String code);

}
