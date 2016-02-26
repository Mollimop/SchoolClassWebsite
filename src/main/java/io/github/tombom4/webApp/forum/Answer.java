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

    public String getAnswererDisplayName() {
        return answerer.getDisplayName();
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

    public static ArrayList<Document> toDocumentArrayList(ArrayList<Answer> answers) {

        ArrayList<Document> documents = new ArrayList<>(answers.size());
        for (int i = 0; i < documents.size(); i++) {
            documents.set(i, answers.get(i).toDocument());
        }

        return documents;
    }

    public static ArrayList<Answer> toAnswerArrayList(ArrayList<Document> documents) {
        ArrayList<Answer> answers = new ArrayList<>(documents.size());

        documents.forEach( doc -> {
            int likes;
            String title, body;
            User answerer;

            title = doc.getString("title");
            answerer = new User(doc.getString("answerer"));
            body = doc.getString("body");
            likes = doc.getInteger("likes");

            answers.add(new Answer(title, answerer, body, likes));
        });

        return answers;
    }

}
