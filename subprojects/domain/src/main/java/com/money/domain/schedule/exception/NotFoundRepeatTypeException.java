package com.money.domain.schedule.exception;

import com.money.exception.ErrorCode;
import com.money.exception.MainException;

public class NotFoundRepeatTypeException extends MainException {

    public NotFoundRepeatTypeException() {
        super(ErrorCode.NOT_FOUND_REPEAT_TYPE);
    }
}
