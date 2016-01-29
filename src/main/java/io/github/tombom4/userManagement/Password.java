package io.github.tombom4.userManagement;

/**
 * Created by Sebastian on 21.01.2016.
 */
public class Password {
    private String encryptedPassword = "";

    public Password(String password){
        encryptPassword(password);
    }

    public static Password byEncryptedPassword(String password) {
        Password psw = new Password("");
        psw.setEncryptedPassword(password);
        return psw;
    }

    public void encryptPassword(String psw){
        char[] passwordChars = psw.toCharArray();
        int[] passwordCode = new int[passwordChars.length];
        long[] passwordCodeEncrypted = new long[passwordChars.length];
        for(int i = 0; i < passwordChars.length; i++){
            passwordCode[i] = passwordChars[i];
            passwordCodeEncrypted[i] = passwordCode[i] * passwordCode[i] * passwordCode[1] * 89222;
            encryptedPassword = encryptedPassword + passwordCodeEncrypted[i];
        }
    }

    public String toString() {
        return encryptedPassword;
    }

    private void setEncryptedPassword(String password) {
        encryptedPassword = password;
    }
}
