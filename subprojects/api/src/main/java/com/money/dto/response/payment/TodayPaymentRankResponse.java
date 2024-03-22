package com.money.dto.response.payment;

import com.money.domain.payment.dto.TodayDepositDto;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;

public record TodayPaymentRankResponse(
        LocalDateTime now,
        Page<TodayDepositDto> todayRank
) {
    public static TodayPaymentRankResponse from(Page<TodayDepositDto> todayRank) {
        return new TodayPaymentRankResponse(LocalDateTime.now(),todayRank);
    }
}
