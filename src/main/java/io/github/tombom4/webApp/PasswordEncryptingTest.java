package io.github.tombom4.webApp;

/**
 * Created by Sebastian on 21.01.2016.
 */

import javax.swing.*;

/**
 * This class shall not be part of the website. It's just for testing.
 */
public class PasswordEncryptingTest {
    public static void main (String... args){
        String password = JOptionPane.showInputDialog("Password please");
        Password p1 = new Password(password);
        JOptionPane.showMessageDialog(null, p1.cryptPassword());
    }


}
