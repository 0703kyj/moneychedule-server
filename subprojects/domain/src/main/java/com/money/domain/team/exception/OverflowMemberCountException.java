package com.money.domain.team.exception;

import com.money.exception.ErrorCode;
import com.money.exception.MainException;

public class OverflowMemberCountException extends MainException {

    public OverflowMemberCountException() {
        super(ErrorCode.OVER_FLOW_MEMBER_COUNT);
    }
}
