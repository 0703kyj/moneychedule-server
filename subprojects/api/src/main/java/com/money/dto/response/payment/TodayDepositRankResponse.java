package com.money.dto.response.payment;

import com.money.domain.payment.dto.TodayDepositDto;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;

public record TodayDepositRankResponse(
        LocalDateTime now,
        Page<TodayDepositDto> todayRank
) {
    public static TodayDepositRankResponse from(Page<TodayDepositDto> todayRank) {
        return new TodayDepositRankResponse(LocalDateTime.now(),todayRank);
    }
}
