package com.money.domain.team.util;

import com.money.domain.member.entity.InviteCode;
import com.money.domain.member.exception.OverFlowInviteCodeCountException;
import java.util.Date;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class RandomCodeGenerator {

    private static final Random random = new Random();
    private static final int CODE_LENGTH = 5;
    private static final int MIN = (int) Math.pow(10, CODE_LENGTH);
    private static final int MAX = 9 * MIN;

    public static String generateRandomCode() {
        return Integer.toString(random.nextInt(MAX) + MIN);
    }
}