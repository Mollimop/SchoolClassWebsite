package io.github.tombom4.webApp.forum;

/**
 * Created by Sebastian on 27.01.2016.
 */
public class Thread {
    private String title;
    private String schoolSubject;
    private User questioner;
    private String question;
    private Answer[] answers = new Answer[0];

    public Thread(String title,String schoolSubject, String question, User questioner){
        this.questioner = questioner;
        this.schoolSubject = schoolSubject;
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

    public String getSchoolSubject(){
        return schoolSubject;
    }

    public User getQuestioner(){
        return questioner;
    }

    public String getQuestion(){
        return question;
    }


}

