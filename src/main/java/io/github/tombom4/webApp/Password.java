package io.github.tombom4.webApp;

/**
 * Created by Sebastian on 21.01.2016.
 */
public class Password {
    private String passwordUncrypted;
    private String passwordCrypted = "";

    public Password(String password){
        passwordUncrypted = password;
    }

    public String cryptPassword(){
        char[] passwordChars = passwordUncrypted.toCharArray();
        int[] passwordCode = new int[passwordChars.length];
        long[] passwordCodeCrypted = new long[passwordChars.length];
        for(int i = 0; i < passwordChars.length; i++){
            passwordCode[i] = passwordChars[i];
            passwordCodeCrypted[i] = passwordCode[i] * passwordCode[i] * passwordCode[1] * 89222;
            passwordCrypted = passwordCrypted + passwordCodeCrypted[i];
        }
        return passwordCrypted;
    }
}
