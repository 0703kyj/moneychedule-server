package com.money.dto.request.schedule;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record ScheduleAttendeeUpdateRequest(
        @Schema(description = "참가자", example = "[1,2]")
        List<Long> members
) {

}
