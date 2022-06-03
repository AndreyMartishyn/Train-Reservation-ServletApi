package ua.martishyn.app.data.utils.validator;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Basic back-end validation class
 * Validation using regular expressions
 * and formatter for dates
 */

public class DataInputValidatorImpl implements DataInputValidator {
    private static final String LOGIN_NAME_REGEX = "^[A-Za-z\\u0400-\\u04ff]{1,16}$";
    private static final String STRING_REGEX = "^([\\p{L}-]*[\\s]*)$";
    private static final String NUM_REGEX = "^\\d{1,10}$";
    private static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_.+-]+@[a-zA-Z-]+\\.[a-zA-Z.]+$";

    /**
     * Checks input for correct
     * letters and amount of input
     *
     * @param name
     * @return true or false
     */
    @Override
    public boolean isValidNameField(String name) {
        if (isNullAndEmpty(name)) {
            return false;
        }
        return name.matches(LOGIN_NAME_REGEX);
    }

    /**
     * Checks input for correct input
     * without special symbols
     *
     * @param password
     * @return true or false
     */

    @Override
    public boolean isValidPasswordField(String password) {
        if (isNullAndEmpty(password)) {
            return false;
        }
        return password.matches(PASSWORD_REGEX);
    }

    /**
     * Checks user input in accordance
     * with correct email form regex
     *
     * @param email
     * @return true or false
     */

    @Override
    public boolean isValidEmailField(String email) {
        if (isNullAndEmpty(EMAIL_REGEX)) {
            return false;
        }
        return email.matches(EMAIL_REGEX);
    }
    /**
     * Checks user input in accordance
     * with correct String form regex
     *
     * @param data
     * @return true or false
     */

    @Override
    public boolean isValidStringInput(String data) {
        if (isNullAndEmpty(data)) {
            return false;
        }
        return data.matches(STRING_REGEX);
    }

    /**
     * Checks user input in accordance
     * with correct number(int) form regex
     * between 1 and 10 quantity
     *
     * @param number
     * @return true or false
     */

    @Override
    public boolean isValidNumInput(String number) {
        if (isNullAndEmpty(number)) {
            return false;
        }
        return number.matches(NUM_REGEX);
    }

    /**
     * Checks date for correct input
     * with correct pattern validation
     *
     * @param departure, arrival
     * @return true or false
     */

    @Override
    public boolean isValidDateInput(String departure, String arrival) {
        if (departure == null || departure.isEmpty() ||
                arrival == null || arrival.isEmpty()) {
            return false;
        }
        try {
            DateFormat formatPattern = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            formatPattern.setLenient(false);
            formatPattern.parse(departure);
            formatPattern.parse(arrival);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    private boolean isNullAndEmpty(String input) {
        return input == null || input.isEmpty();
    }
}
