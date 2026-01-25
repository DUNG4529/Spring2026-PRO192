package Model;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

public class ValidationInput {
    // check ID
    public static String validID(String id) {
        String regexID = "^[A-Z]{2}\\d{6}";
        if (id == null || !id.matches(regexID))
            throw new IllegalArgumentException("ID not valid!");
        return id;
    }

    // check Name
    public static String validName(String name) {
        if (name == null)
            throw new IllegalArgumentException("Name not empty!");
        String trimmedName = name.trim();
        String regexName = "^[a-zA-Z]+(\\s[A-Za-z]+)*$";
        if (!trimmedName.matches(regexName) || trimmedName.length() > 50)
            throw new IllegalArgumentException("Name not valid (name <50)1!");
        return trimmedName;
    }

    // check Salary
    public static double validSalary(double basicSalary) {
        if (basicSalary <= 0)
            throw new IllegalArgumentException("Salary not valid!");
        return basicSalary;
    }

    // Valid Day
    public static LocalDate validDate(LocalDate date) {
        if (date == null || date.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Date not valid!");
        return date;
    }
}