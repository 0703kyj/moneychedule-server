package com.money.domain.team.exception;

import com.money.exception.ErrorCode;
import com.money.exception.MainException;

public class NotFoundTeamException extends MainException {

    public NotFoundTeamException() {
        super(ErrorCode.NOT_FOUND_TEAM);
    }
}
