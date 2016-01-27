package io.github.tombom4.webApp.forum;

/**
 * Created by Sebastian on 27.01.2016.
 */
public class Thread {
    private String title;
    private String questioner;
    private String question;
    private Answer[] answers = new Answer[0];

    public Thread(String title, String question, String questioner){
        this.questioner = questioner;
        this.title = title;
        this.question = question;
    }

    public void addAnswer(Answer answer){
        answers[answers.length] = answer;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setQuestion(String question){
        this.question = question;
    }

    public Answer getAswerN(int whichAnswer){
        if(whichAnswer > answers.length){
            return null;
        }else{
            return answers[whichAnswer - 1];
        }
    }

    public String getTitle(){
        return title;
    }

    public String getQuestioner(){
        return questioner;
    }

    public String getQuestion(){
        return question;
    }


}

