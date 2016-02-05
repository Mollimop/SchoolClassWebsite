package io.github.tombom4.userManagement;

/**
 * Created by Sebastian on 21.01.2016.
 */
public class Password {
    private String encryptedPassword;

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
            if(i == 0 || i == 1){
                passwordCodeEncrypted[i] = passwordCode[i] * passwordCode[i] * passwordCode[i] * 892222;
            }else {
                passwordCodeEncrypted[i] = passwordCode[i] * passwordCode[0] * passwordCode[1] * 89222;
            }

            if(i == 0){
                encryptedPassword = String.valueOf(passwordCodeEncrypted[i]);
            }else {
                encryptedPassword = encryptedPassword + passwordCodeEncrypted[i];
            }
        }
    }

    public String toString() {
        return encryptedPassword;
    }

    private void setEncryptedPassword(String password) {
        encryptedPassword = password;
    }
}
