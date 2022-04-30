package ua.martishyn.app.data.utils.validator;

/**
 * Basic back-end validation class
 * Validation using regular expressions
 */

public class DataInputValidatorImpl implements DataInputValidator {
    private static final String NAME_REGEX = "[A-Za-z\\u0400-\\u04ff]{1,16}";
    private static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";

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
            return name.matches(NAME_REGEX);
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
     * Checks input for correct input
     * with correct email form
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

    private boolean isNullAndEmpty(String input) {
        return input == null || input.isEmpty();
    }
}
