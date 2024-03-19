package com.money.service.member;

import com.money.domain.InviteCode;
import com.money.exception.OverFlowInviteCodeCountException;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

public class InviteCodeProvider {

    private static final Set<InviteCode> generatedCodes = new HashSet<>();
    private static final Random random = new Random();
    private static final int CODE_LENGTH = 5;
    private static final int MIN = (int) Math.pow(10, CODE_LENGTH);
    private static final int MAX = 9 * MIN;

    public static InviteCode getInviteCode() {
        while (true) {
            InviteCode inviteCode = InviteCode.from(generateRandomCode());

            if (isExpiredCode(inviteCode)|| isNotGeneratedCode(inviteCode)) {
                return inviteCode;
            }
            validateGenerateCode();
        }
    }

    private static boolean isNotGeneratedCode(InviteCode inviteCode) {
        Optional<InviteCode> findCode = findExistCode(inviteCode);

        if (findCode.isEmpty()) {
            generatedCodes.add(inviteCode);
            return true;
        }
        return false;
    }

    private static boolean isExpiredCode(InviteCode inviteCode) {
        Optional<InviteCode> findCode = findExistCode(inviteCode);
        Date now = new Date();

        if (findCode.isPresent() && now.after(findCode.get().getExpiredTime())) {
            generatedCodes.remove(findCode.get());
            generatedCodes.add(inviteCode);
            return true;
        }
        return false;
    }

    private static void validateGenerateCode() {
        if (generatedCodes.size() >= MAX) {
            throw new OverFlowInviteCodeCountException();
        }
    }

    private static int generateRandomCode() {
        return random.nextInt(MAX) + MIN;
    }

    private static Optional<InviteCode> findExistCode(InviteCode inviteCode) {
        return generatedCodes.stream()
                .filter(code -> code.equals(inviteCode))
                .findFirst();
    }
}
