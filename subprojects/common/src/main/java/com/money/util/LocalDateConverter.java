package com.money.util;

import com.money.exception.InvalidDateException;
import java.time.DateTimeException;
import java.time.LocalDate;

public class LocalDateConverter {

    public static LocalDate getLocalDate(int year, int month) {
        try {
            return LocalDate.of(year, month, 1);
        } catch (DateTimeException e) {
            throw new InvalidDateException();
        }
    }
}
