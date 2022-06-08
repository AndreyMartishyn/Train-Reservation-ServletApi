package ua.martishyn.app.data.utils.validator;


public interface DataInputValidator {
    boolean isValidNameField(String name);

    boolean isValidPasswordField(String password);

    boolean isValidEmailField(String email);

    boolean isValidStationNameInput(String data);

    boolean isValidStationCodeInput(String data);

    boolean isValidNumInput(String number);

    boolean isValidDateInput(String departure, String arrival);

}
