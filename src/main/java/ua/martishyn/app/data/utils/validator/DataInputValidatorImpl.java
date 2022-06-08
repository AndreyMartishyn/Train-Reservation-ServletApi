package ua.martishyn.app.data.utils.validator;


import ua.martishyn.app.data.utils.constants.DateConstants;

import java.time.LocalDateTime;

/**
 * Basic back-end validation class
 * Validation using regular expressions
 * and formatter for dates
 */

public class DataInputValidatorImpl implements DataInputValidator {
    private static final String LOGIN_NAME_REGEX = "^[A-Za-z\\u0400-\\u04ff]{1,16}$";
    private static final String STATION_NAME_REGEX = "^[\\p{L}']*(?:[\\s-]\\p{L}*)$";
    private static final String STATION_CODE_REGEX = "^[\\p{Lu}]{3}$";
    private static final String NUM_REGEX = "^\\d{1,10}$";
    private static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,16}$";
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_.-]{2,20}+@[a-zA-Z-]{5,7}+\\.[a-zA-Z.]{2,3}+$";

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
    public boolean isValidStationNameInput(String data) {
        if (isNullAndEmpty(data)) {
            return false;
        }
        return data.matches(STATION_NAME_REGEX);
    }

    /**
     * Checks user input in accordance
     * with correct regex for station code
     *
     * @param data
     * @return true or false
     */

    @Override
    public boolean isValidStationCodeInput(String data) {
        if (isNullAndEmpty(data)) {
            return false;
        }
        return data.matches(STATION_CODE_REGEX);
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
            final LocalDateTime departureDate = LocalDateTime.parse(departure, DateConstants.formatterForLocalDate);
            final LocalDateTime arrivalDate = LocalDateTime.parse(arrival, DateConstants.formatterForLocalDate);
            if (departureDate.isBefore(arrivalDate) || arrivalDate.isBefore(LocalDateTime.now()) || departureDate.isEqual(LocalDateTime.now())
                    || departureDate.getYear() != arrivalDate.getYear() || departureDate.getMonthValue() != arrivalDate.getMonthValue()) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private boolean isNullAndEmpty(String input) {
        return input == null || input.isEmpty();
    }
}
