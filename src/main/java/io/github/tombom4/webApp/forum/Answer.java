package io.github.tombom4.webApp.forum;

import io.github.tombom4.userManagement.User;

/**
 * Created by Sebastian on 27.01.2016.
 */
public class Answer {
    private String title;
    private User answerer;
    private String answer;

    public Answer(String title, User answerer, String answer){
        this.title = title;
        this.answer = answer;
        this.answerer = answerer;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setAnswer(String answer){
        this.answer = answer;
    }

    public String getTitle(){
        return title;
    }

    public User getAnswerer(){
        return  answerer;
    }

    public String getAnswer(){
        return answer;
    }

}
