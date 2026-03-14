package utils;

import java.util.regex.Pattern;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class Validation {
    private static final DateTimeFormatter INPUT_DATE_FORMATTER =
            new DateTimeFormatterBuilder().parseStrict().appendPattern("d/M/uuuu").toFormatter().withResolverStyle(ResolverStyle.STRICT);

    // 1) Validate employee ID (pattern: 2 uppercase letters + 6 digits)
    public static boolean validID(String id) {
        String regexID = "^[A-Z]{2}\\d{6}$";
        return Pattern.matches(regexID, id);
    }

    // 2) Validate name (Unicode letters and spaces, 3-50 characters)
    public static boolean validName(String name) {
        String regexName = "^[\\p{L} ]{3,50}$";  // \p{L} matches any Unicode letter
        return Pattern.matches(regexName, name);
    }

    // 3) Validate salary/amount values (must be >= 0)
    public static boolean validSalary(double salary) {
        return salary >= 0;
    }

    // 4) Validate non-empty string (used for Department, Job Title - BR2)
    public static boolean validString(String input) {
        return input != null && !input.trim().isEmpty();
    }

    // 5) Validate date with real calendar checks (reject invalid dates like 30/02/2023)
    public static boolean validDate(String date) {
        if (date == null || date.trim().isEmpty()) {
            return false;
        }
        try {
            String normalizedDate = date.trim().replaceAll("\\s+", "");
            LocalDate.parse(normalizedDate, INPUT_DATE_FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    // 6) Validate numeric menu choice
    // Example: status menu 1-3 or main menu 1-5
    public static boolean validChoice(int choice, int min, int max) {
        return choice >= min && choice <= max;
    }

    // 7) Validate attendance status (required for BR5 core data rules)
    public static boolean validAttendanceStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            return false;
        }
        String s = status.trim().toLowerCase();
        return s.equals("present") || s.equals("absent") || s.equals("leave");
    }

    // 8) Validate whether input is a valid floating-point number
    public static boolean validNumber(String input) {
        if (input == null || input.trim().isEmpty()) {
            return false;
        }
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // 9) Standardized UI error output helper
    public static void showError(String message) {
        System.out.println("Fail: " + message);
    }
}