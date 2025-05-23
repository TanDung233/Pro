package Server.Utility;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Hashes password using SHA-224.
 */
public class PasswordHasher {
    /**
     * Hashes password with SHA-224.
     * @param password Plain text password.
     * @return Hashed password as hex string.
     */
    public static String hashPassword(String password) {
        try {
            byte[] bytes = password.getBytes();
            MessageDigest md = MessageDigest.getInstance("SHA-224");
            byte[] hashedBytes = md.digest(bytes);
            BigInteger integers = new BigInteger(1, hashedBytes);
            String newPassword = integers.toString(16);

            // Ensure the hex string is 56 characters (224 bits)
            while (newPassword.length() < 56) {
                newPassword = "0" + newPassword;
            }

            return newPassword;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-224 algorithm not available", e);
        }
    }
}
