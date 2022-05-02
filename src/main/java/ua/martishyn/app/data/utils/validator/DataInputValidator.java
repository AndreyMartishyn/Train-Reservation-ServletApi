package ua.martishyn.app.data.utils.validator;

import java.util.Date;

public interface DataInputValidator {
    boolean isValidNameField(String name);

    boolean isValidPasswordField(String password);

    boolean isValidEmailField(String email);

    boolean isValidNumInput(String number);

    boolean isValidDateInput(String departure, String arrival);

}
