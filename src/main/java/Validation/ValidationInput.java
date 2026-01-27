package Validation;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;
// Using Boolean Regex Pattern to validate input
public class ValidationInput {
    // Valid ID
    public static Boolean validID(String id) {
        String regexID = "^[A-Z]{2}\\d{6}";
        return Pattern.matches(regexID, id);
    }

    // Valid Name
    public static Boolean validName(String name) {
        String regexName = "^[a-zA-Z ]{3,50}$";
        return Pattern.matches(regexName, name);
    }

    // Valid Salary
    public static Boolean validSalary(double salary) {
        return salary >= 0;
    }

    // Valid String
    public static Boolean validString(String input) {
        return input != null && !input.trim().isEmpty();
    }

    // Valid Day 
    public static Boolean validDate(String date) {
        if (date == null || date.isEmpty()) {
            return false;
        }
        String regexDate = "^\\d{2}/\\d{2}/\\d{4}$";
        return Pattern.matches(regexDate, date);
    }

    // Valid is Number 
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

}