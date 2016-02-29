package io.github.tombom4.webApp;

import io.github.tombom4.webApp.Event;
import org.bson.Document;

import java.util.Date;

/**
 * @author Sebastian Vogt
 */
public class Homework extends Event {

    private String subject;

    public Homework(Date date, String description, Boolean insert, String subject){
        super(date, description, insert);
        this.subject = subject;
        setSubject(subject);
    }

    public Homework(Document doc){
        super(doc);
        /* todo: implement subject */
    }

    public Homework(String id){
        super(id);
        /* todo: implement subject */
    }

    public void setSubject(String subject){
        if(subject.equals("Deutsch") || subject.equals("Mathe") || subject.equals("Englisch")
                || subject.equals("Französisch") || subject.equals("Physik") || subject.equals("Italienisch")
                || subject.equals("Chemie") || subject.equals("Biologie") || subject.equals("Informatik")
                || subject.equals("Geschichte") || subject.equals("Wirtschaft und Recht") || subject.equals("Religion")){
            this.subject = subject;
        }else{
            throw new IllegalArgumentException(subject + " is not a subject");
        }
    }
}
