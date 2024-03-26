package com.money.domain.schedule.exception;

import com.money.exception.ErrorCode;
import com.money.exception.MainException;

public class NotFoundScheduleException extends MainException {

    public NotFoundScheduleException() {
        super(ErrorCode.NOT_FOUND_SCHEDULE);
    }
}
