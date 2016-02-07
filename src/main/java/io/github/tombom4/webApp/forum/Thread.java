package io.github.tombom4.webApp.forum;

import io.github.tombom4.userManagement.User;

/**
 * @author Sebastian Vogt
 */
public class Thread {
    private String title;
    private String schoolSubject;
    private User questioner;
    private String question;
    private User[] visibleFor;
    private Answer[] answers = new Answer[0];
    private boolean open;

    public Thread(String title, String schoolSubject, String question, User questioner, User[] visibleFor, Answer[] answers, boolean open){
        this.questioner = questioner;
        this.schoolSubject = schoolSubject;
        this.title = title;
        this.question = question;
        this.visibleFor = visibleFor;
        this.answers = answers;
        this.open = open;
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

    public void setVisibleFor(User[] visibleFor){
        this.visibleFor = visibleFor;
    }

    public void setOpen(boolean open){
        this.open = open;
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

    public User[] getVisibleFor(){
        return visibleFor;
    }

    public boolean isOpen(){
        return open;
    }


}

