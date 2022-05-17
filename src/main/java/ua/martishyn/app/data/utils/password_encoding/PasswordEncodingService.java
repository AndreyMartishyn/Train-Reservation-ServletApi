package ua.martishyn.app.data.utils.password_encoding;

import org.jetbrains.annotations.NotNull;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class PasswordEncodingService {

    private PasswordEncodingService() {
    }

    public static String makeHash(String password) {
        String encodedPass = null;
        try {
            encodedPass = PasswordEncoder.makeHash(password);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException  exception) {
            exception.printStackTrace();
        }
        return encodedPass;
    }
}
