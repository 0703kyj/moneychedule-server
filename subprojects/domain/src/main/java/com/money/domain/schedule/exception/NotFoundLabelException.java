package com.money.domain.schedule.exception;

import com.money.exception.ErrorCode;
import com.money.exception.MainException;

public class NotFoundLabelException extends MainException {

    public NotFoundLabelException() {
        super(ErrorCode.NOT_FOUND_LABEL);
    }
}
