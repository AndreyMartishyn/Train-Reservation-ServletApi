package ua.martishyn.app.data.utils.validator;


public interface DataInputValidator {
    boolean isValidNameField(String name);

    boolean isValidPasswordField(String password);

    boolean isValidEmailField(String email);

    boolean isValidStringInput(String data);

    boolean isValidNumInput(String number);

    boolean isValidDateInput(String departure, String arrival);

}
