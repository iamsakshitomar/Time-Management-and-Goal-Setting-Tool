package timemanagement;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public final class Validator {
    private Validator() {}

    public static void validateRequired(String value, String field)
            throws InvalidTaskException {
        if (value == null || value.trim().isEmpty()) {
            throw new InvalidTaskException(field + " cannot be empty.");
        }
    }

    public static void validateDate(String value, String field)
            throws InvalidTaskException {
        validateRequired(value, field);
        try {
            LocalDate.parse(value);
        } catch (DateTimeParseException e) {
            throw new InvalidTaskException(field + " must use YYYY-MM-DD format.");
        }
    }

    public static int minutes(String value) throws InvalidTaskException {
        try {
            int n = Integer.parseInt(value);
            if (n <= 0) throw new NumberFormatException();
            return n;
        } catch (NumberFormatException e) {
            throw new InvalidTaskException("Estimated time must be a positive number of minutes.");
        }
    }
}
