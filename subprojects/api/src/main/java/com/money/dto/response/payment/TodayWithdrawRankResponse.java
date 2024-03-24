package com.money.dto.response.payment;

import com.money.domain.payment.dto.TodayWithdrawDto;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;

public record TodayWithdrawRankResponse(
        LocalDateTime now,
        Page<TodayWithdrawDto> todayRank
) {
    public static TodayWithdrawRankResponse from(Page<TodayWithdrawDto> todayRank) {
        return new TodayWithdrawRankResponse(LocalDateTime.now(),todayRank);
    }
}
