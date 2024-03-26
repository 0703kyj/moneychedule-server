package com.money.domain.schedule.entity.enums;

import com.money.domain.schedule.exception.NotFoundRepeatTypeException;

public enum RepeatType {
    NONE("없음"), DAY("매일"), WEEK("매주"), MONTH("매달"), YEAR("매년");

    private String value;

    RepeatType(String value) {
        this.value = value;
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
