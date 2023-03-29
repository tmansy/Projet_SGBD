/**
 * Classe permettant de hasher un mot de passe
 */

package singleton;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHash {
    /**
     * Méthode permettant de se hasher son mot de passe avec l'algorithme MD5
     */
    public static String getSecurePassword(String password) {
        BigInteger bigInt = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(password.getBytes());
            bigInt = new BigInteger(1, messageDigest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return bigInt.toString(16);
    }
}
