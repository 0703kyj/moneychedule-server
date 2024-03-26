package com.money.dto.request.schedule;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

public record ScheduleRequest(
        @Schema(description = "라벨Id", example = "1")
        Long labelId,
        @Schema(description = "세부 내용", example = "메모")
        String memo,
        @DateTimeFormat(iso = ISO.DATE_TIME)
        @Schema(description = "시작일", example = "yyyy-MM-dd")
        LocalDate startDate,
        @DateTimeFormat(iso = ISO.TIME)
        @Schema(description = "시작시간", example = "07:30:00")
        LocalTime startTime,
        @DateTimeFormat(iso = ISO.DATE_TIME)
        @Schema(description = "종료일", example = "yyyy-MM-dd")
        LocalDate endDate,
        @DateTimeFormat(iso = ISO.TIME)
        @Schema(description = "종료시간", example = "07:30:00")
        LocalTime endTime,
        @Schema(description = "반복 종류", example = "없음")
        String repeatType,
        @Schema(description = "참가자", example = "[1,2]")
        List<Long> members
) {

}
