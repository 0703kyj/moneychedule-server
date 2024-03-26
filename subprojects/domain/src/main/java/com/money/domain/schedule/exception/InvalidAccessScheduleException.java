package com.money.domain.schedule.exception;

import com.money.exception.ErrorCode;
import com.money.exception.MainException;

public class InvalidAccessScheduleException extends MainException {

    public InvalidAccessScheduleException() {
        super(ErrorCode.INVALID_ACCESS_SCHEDULE);
    }
}
