package io.github.tombom4.webApp;

import io.github.tombom4.webApp.Event;
import org.bson.Document;

import java.util.Date;

/**
 * @author Sebastian Vogt
 */
public class Homework extends Event {

    private static Database db;

    private String subject;

    public Homework(Date date, String description, String subject, Boolean insert) {
        super(date, description, insert);
        this.subject = subject;
        setSubject(subject);
    }

    public Homework(Document doc) {
        super(doc);
        subject = doc.getString("subject");
    }

    public Homework(String id) {

        super(id);

        ObjectId objectId = new ObjectId(id);
        MongoCollection<Document> homework = db.getHomework();
        this.id = id;
        this.date = homework.find(new Document("_id", id)).first().getDate("date");
        this.description = homework.find(new Document("_id", id)).first().getString("description");
        this.subject = homework.find(new Document("_id", id).first().getString("subject"))

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        String year = Integer.toString(cal.get(YEAR));
        String month = Integer.toString(cal.get(MONTH) + 1);
        if (month.length() < 2) month = "0" + month;
        String day = Integer.toString(cal.get(DAY_OF_MONTH));
        if (day.length() < 2) day = "0" + day;

        dateString = day + "." + month + "." + year;

    }

    public static void init(Database db) {
        this.db = db;
    }

    public void setSubject(String subject) {
        if (subject.equals("Deutsch") || subject.equals("Mathe") || subject.equals("Englisch")
                || subject.equals("Französisch") || subject.equals("Physik") || subject.equals("Italienisch")
                || subject.equals("Chemie") || subject.equals("Biologie") || subject.equals("Informatik")
                || subject.equals("Geschichte") || subject.equals("Wirtschaft und Recht") || subject.equals("Religion")){
            this.subject = subject;
        } else {
            throw new IllegalArgumentException(subject + " is not a subject");
        }
    }
}
