package io.github.tombom4.webApp;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.UpdateOptions;
import io.github.tombom4.userManagement.Database;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author Sebastian Vogt
 */
public class Homework extends Event {

    private static Database db;

    private String subject;

    public Homework(Date date, String description, String subject, Boolean insert) {
        super(date, description, false);
        this.subject = subject;
        setSubject(subject);

        if (insert) {

            MongoCollection<Document> collection = db.getHomework();

            Bson filter = new Document("_id", new ObjectId(this.getId()));
            Bson update = new Document("$set", new Document()
                    .append("date", date)
                    .append("description", description)
                    .append("subject", subject));
            UpdateOptions options = new UpdateOptions().upsert(true);

            collection.updateOne(filter, update, options);

        }
    }

    public Homework(Document doc) {
        super(doc);
        subject = doc.getString("subject");
    }

    public Homework(String id) {

        super(null, null, false);

        ObjectId objectId = new ObjectId(id);
        MongoCollection<Document> homework = db.getHomework();

        this.setDate(homework.find(new Document("_id", id))
            .first().getDate("date"));
        this.setDescription(homework.find(new Document("_id", id))
            .first().getString("description"));
        this.setSubject(homework.find(new Document("_id", id))
            .first().getString("subject"));

        updateDateString();

    }

    public static void removeHomework(String id) {
            ObjectId objectId = new ObjectId(id);
            db.getHomework().deleteOne(new Document("_id", objectId));
    }

    public String getSubject() {
        return subject;
    }

    public static void init(Database db) {
        Homework.db = db;
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

    public static ArrayList<Homework> getNextHomework(int number) {
        MongoCollection<Document> collection = db.getHomework();

        Bson filter = Filters.gte("date", new Date());
        Bson sort = Sorts.ascending("date");

        ArrayList<Document> documents = collection.find(filter)
            .sort(sort).limit(number).into(new ArrayList<>());
        ArrayList<Homework> homework = new ArrayList<>(documents.size());
        documents.forEach(document -> homework.add(new Homework(document)));

        return homework;
    }

    public static ArrayList<Homework> getNextHomework() {
        return getNextHomework(10);
    }

}
