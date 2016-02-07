package io.github.tombom4.webApp.forum;

import io.github.tombom4.userManagement.User;

/**
 * @author Sebastian Vogt
 */
public class Answer {
    private String title;
    private User answerer;
    private String answer;
    private int likes;

    public Answer(String title, User answerer, String answer, int likes){
        this.title = title;
        this.answer = answer;
        this.answerer = answerer;
        this.likes = likes;
    }
    
    public Answer(String title, User answerer, String answer){
        this(title, answerer, answer, 0);
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setAnswer(String answer){
        this.answer = answer;
    }

    public void like(){
        likes = likes + 1;
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

    public int getLikes(){
        return likes;
    }

}
