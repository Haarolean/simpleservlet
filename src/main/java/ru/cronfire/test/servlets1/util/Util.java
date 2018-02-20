package ru.cronfire.test.servlets1.util;

import java.util.regex.Pattern;

public class Util {

    private static final Pattern VALID_DATE = Pattern.compile("^\\w{3} \\d{2}, \\d{4}$");

    public static boolean isNotEmpty(final String input) {
        return input != null && !input.isEmpty();
    }

    public static boolean isValidDate(final String date) {
        return VALID_DATE.matcher(date).matches();
    }

    public static boolean isValidInt(final String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException ignored) {
            return false;
        }
    }
}
