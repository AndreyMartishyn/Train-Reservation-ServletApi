package ua.martishyn.app.utils;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import ua.martishyn.app.data.utils.password_encoding.PasswordEncoder;
import ua.martishyn.app.data.utils.password_encoding.PasswordEncodingService;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class PasswordEncodingTest {
    private static final String HASH = "9ad2d8421aa8271565041d5b4cf0853b6b2f38a6fd721698631e09af320c245a";
    private static final String CORRECT_ALGORITHM = "PBKDF2WithHmacSHA1";
    private static final String WRONG_ALGORITHM = "WRONG";
    //https://www.freecodeformat.com/pbkdf2.php site for encoding

    @Test
    public void shouldHashSamePasswordWhenSameProperties() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String actual = PasswordEncodingService.makeHash("helloTest");
        Assert.assertEquals(HASH, actual);
    }

    @Test(expected = NoSuchAlgorithmException.class)
    public void shouldThrowExceptionWhenAlgorithmIsStrange() throws NoSuchAlgorithmException, InvalidKeySpecException {
        PasswordEncoder.setAlgorithm(WRONG_ALGORITHM);
        PasswordEncoder.makeHash("helloTest");
    }

    @After
    public void tearDown() {
        PasswordEncoder.setAlgorithm(CORRECT_ALGORITHM);
    }
}
