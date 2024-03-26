package com.money.domain.schedule.repository;

import com.money.domain.schedule.entity.Label;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LabelRepository extends JpaRepository<Label,Long> {

}
