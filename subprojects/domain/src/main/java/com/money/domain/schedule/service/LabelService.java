package com.money.domain.schedule.service;

import com.money.domain.schedule.entity.Label;
import com.money.domain.schedule.exception.NotFoundLabelException;
import com.money.domain.schedule.repository.LabelRepository;
import com.money.domain.team.entity.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LabelService {

    private final LabelRepository labelRepository;

    public Label findById(Long labelId) {
        return labelRepository.findById(labelId)
                .orElseThrow(NotFoundLabelException::new);
    }

    public void initLabels(Team team) {
        labelRepository.save(Label.of(team, "name", "color"));
    }
}
