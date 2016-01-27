package io.github.tombom4.webApp.forum;

/**
 * Created by Sebastian on 27.01.2016.
 */
public class Answer {
    private String title;
    private String answerer;
    private String answer;

    public Answer(String title, String answerer, String answer){
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

    public String getAnswerer(){
        return  answerer;
    }

    public String getAnswert(){
        return answer;
    }

}
