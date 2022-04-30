package ua.martishyn.app.data.utils.validator;

public interface DataInputValidator {
    boolean isValidNameField(String name);

    boolean isValidPasswordField(String password);

    boolean isValidEmailField(String email);

}
