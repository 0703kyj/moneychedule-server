package com.money.domain.schedule.entity.enums;

import com.money.domain.schedule.exception.NotFoundRepeatTypeException;
import lombok.Getter;

@Getter
public enum RepeatType {
    NONE("없음", 1), DAY("매일",180), WEEK("매주",60), MONTH("매달",30), YEAR("매년",15);

    private final String value;
    private final int repeatCount;

    RepeatType(String value, int repeatCount) {
        this.value = value;
        this.repeatCount = repeatCount;
    }

    public static RepeatType fromString(String value) {
        for (RepeatType repeat : RepeatType.values()) {
            if (repeat.value.equalsIgnoreCase(value)) {
                return repeat;
            }
        }
        throw new NotFoundRepeatTypeException();
    }
}
