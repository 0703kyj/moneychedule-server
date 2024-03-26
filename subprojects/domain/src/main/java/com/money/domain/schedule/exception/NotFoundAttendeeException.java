package com.money.domain.schedule.exception;

import com.money.exception.ErrorCode;
import com.money.exception.MainException;

public class NotFoundAttendeeException extends MainException {

    public NotFoundAttendeeException() {
        super(ErrorCode.NOT_FOUND_ATTENDEE);
    }
}
