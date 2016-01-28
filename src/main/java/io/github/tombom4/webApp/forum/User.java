package io.github.tombom4.webApp.forum;

import io.github.tombom4.webApp.Password;

/**
 * Created by Sebastian on 28.01.2016.
 */
public class User {
    private String name;
    private String mail;
    private Password password;
    private boolean teacher;

    public User(String name, String mail, Password password, boolean teacher){
        this.name = name;
        this.mail = mail;
        this.teacher = teacher;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setMail(String mail){
        this.mail = mail;
    }

    public void setPassword(Password password){
        this.password = password;
    }

    public void setTeacher(boolean teacher){
        this.teacher = teacher;
    }

    public String getName(){
        return name;
    }

    public String getMail(){
        return mail;
    }

    public Password getPassword(){
        return password;
    }

    public boolean isTeacher(){
        return teacher;
    }


}
