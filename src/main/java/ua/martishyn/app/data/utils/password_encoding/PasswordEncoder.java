package ua.martishyn.app.data.utils.password_encoding;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class PasswordEncoder {
    public static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA1";
    public static final String SALT = "m6bv8*1fa";
    public static final int HASH_BYTES = 32;
    public static final int PBKDF2_ITERATIONS = 1000;


    public static String makeHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {return makeHash(password.toCharArray(), SALT.getBytes(StandardCharsets.UTF_8));
    }

    private static String makeHash(char[] password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] hash = pbkdf2(password, salt, PBKDF2_ITERATIONS, HASH_BYTES);
        return toHex(hash);
    }

    /**
     * Computes the PBKDF2 hash of a password.
     *
     * @param password   the password to hash.
     * @param salt       the salt
     * @param iterations the iteration count (slowness factor)
     * @param bytes      the length of the hash to compute in bytes
     * @return the PBDKF2 hash of the password
     */
    private static byte[] pbkdf2(char[] password, byte[] salt, int iterations, int bytes)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeySpec spec = new PBEKeySpec(password, salt, iterations, bytes * 8);
        try {
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
            return secretKeyFactory.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("Missing algorithm: " + PBKDF2_ALGORITHM + ex);
            throw new NoSuchAlgorithmException();
        } catch (InvalidKeySpecException ex) {
            System.out.println("Invalid SecretKeyFactory " + ex);
            throw new InvalidKeySpecException();
        }
    }

    /**
     * Converts a byte array into a hexadecimal string.
     *
     * @param array the byte array to convert
     * @return a length*2 character string encoding the byte array
     */
    private static String toHex(byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        } else {
            return hex;
        }
    }
}
