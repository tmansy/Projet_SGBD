/**
 * Classe permettant les vérifications regex
 */

package singleton;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    /**
     * Méthode permettant de se vérifier l'adresse email
     */
    public static boolean isValidEmail(String email){
        String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * Méthode permettant de se vérifier le nom d'utilisateur
     */
    public static boolean isValidUsername(String username){
        String regex = "^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(username);
        return m.matches();
    }

    /**
     * Méthode permettant de se vérifier le mot de passe
     */
    public static boolean isValidPassword(String password){
        String regex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(password);
        return m.matches();
    }
}
