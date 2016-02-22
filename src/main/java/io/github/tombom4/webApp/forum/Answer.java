package io.github.tombom4.webApp.forum;

import io.github.tombom4.userManagement.User;
import org.bson.Document;

import java.util.ArrayList;

/**
 * @author Sebastian Vogt, Thomas Kirz
 */
public class Answer {
    private String title;
    private User answerer;
    private String body;
    private int likes;

    public Answer(String title, User answerer, String body, int likes){
        this.title = title;
        this.body = body;
        this.answerer = answerer;
        this.likes = likes;
    }
    
    public Answer(String title, User answerer, String body){
        this(title, answerer, body, 0);
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setBody(String body){
        this.body = body;
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

    public String getBody(){
        return body;
    }

    public int getLikes(){
        return likes;
    }

    public Document toDocument() {

        return new Document("title", title)
                .append("answerer", answerer.getName())
                .append("body", body)
                .append("likes", likes);
    }

    public static ArrayList<Document> toDocumentArrayList(Answer[] answers) {

        ArrayList<Document> documents = new ArrayList<>(answers.length);
        for (int i = 0; i < documents.size(); i++) {
            documents.set(i, answers[i].toDocument());
        }

        return documents;
    }

    public static Answer[] toAnswerArray(ArrayList<Document> documents) {
        Answer[] answers = new Answer[documents.size()];

        for (int i = 0; i < documents.size(); i++) {

            int likes;
            String title, body;
            User answerer;

            Document document = documents.get(i);

            title = document.getString("title");
            answerer = new User(document.getString("answerer"));
            body = document.getString("body");
            likes = document.getInteger("likes");

            answers[i] = new Answer(title, answerer, body, likes);

        }

        return answers;
    }

}
