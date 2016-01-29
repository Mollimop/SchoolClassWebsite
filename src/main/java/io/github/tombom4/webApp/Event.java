package io.github.tombom4.webApp;

import com.mongodb.client.MongoCollection;
import io.github.tombom4.userManagement.Database;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.Date;

/**
 * This class implements an event of the events page
 *
 * @author Thomas Kirz
 */

public class Event {

    /**
     * The database used for saving and retrieving the events
     */
    private static Database db;

    /**
     * The unique identifier of the event (_id field in the database)
     */
    private final ObjectId id = new ObjectId();
    /**
     * The date of the eveent
     */
    private Date date;
    /**
     * A short description of the event
     */
    private String description;

    /**
     * Creates a new event with given date and description
     *
     * @param date        the date of the event
     * @param description a short description of the event
     */
    public Event(Date date, String description) {
        this.date = date;
        this.description = description;

        MongoCollection<Document> collection = db.getEvents();

        Document document = new Document("_id", id)
                .append("date", date)
                .append("description", description);
        collection.insertOne(document);
    }

    /**
     * Retrieves the event with the given id from the server
     *
     * @param id the unique identifier of the event
     */
    public Event(String id) {
        MongoCollection<Document> collection = db.getEvents();
        Document doc = collection.find(new Document("_id", id)).first();

        new Event(doc.getDate("date"), doc.getString("description"));
    }

    /**
     * Gets the id of the event
     *
     * @return the id of the event
     */
    public ObjectId getId() {
        return id;
    }

    /**
     * Gets the date of the event
     *
     * @return the date of the event
     */
    public Date getDate() {
        return date;
    }

    /**
     * Gets the description of the event
     *
     * @return the description of the event
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the date of the event
     *
     * @param date the new date of the events
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Sets the description of the event
     *
     * @param description the new description of the events
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Initializes the database field
     *
     * @param db the database
     */
    public static void init(Database db) {
        Event.db = db;
    }

    /**
     * Saves the event in the database after a change of the event
     */
    private void save() {
        MongoCollection<Document> collection = db.getEvents();

        Bson filter = new Document("_id", id);
        Bson set = new Document("$set",
                new Document("date", date)
                        .append("description", description));
        collection.updateOne(filter, set);
    }
}
