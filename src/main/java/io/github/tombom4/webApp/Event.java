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
import java.util.Calendar;
import java.util.Date;

import static java.util.Calendar.*;

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
    private final String id;

    /**
     * The date of the eveent
     */
    private Date date;

    /**
     * The date of the event as a DD.MM.YYYY string
     */
    private String dateString;

    /**
     * A short description of the event
     */
    private String description;


    /**
     * Creates a new event with given date and description and optionally stores it in the database
     *
     * @param date        the date of the event
     * @param description a short description of the event
     * @param insert      specify if the document should be inserted in the database (use <code>false</code> if the
     *                    document already exists in the database
     */
    public Event(Date date, String description, Boolean insert) {
        this.date = date;
        this.description = description;
        this.id = new ObjectId().toString();

        if (insert) {

            MongoCollection<Document> collection = db.getEvents();

            Bson filter = new Document("_id", new ObjectId(id));
            Bson update = new Document("$set", new Document()
                    .append("date", date)
                    .append("description", description));
            UpdateOptions options = new UpdateOptions().upsert(true);

            collection.updateOne(filter, update, options);

        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        String year = Integer.toString(cal.get(YEAR));
        String month = Integer.toString(cal.get(MONTH) + 1);
        if (month.length() < 2) month = "0" + month;
        String day = Integer.toString(cal.get(DAY_OF_MONTH));
        if (day.length() < 2) day = "0" + day;

        dateString = day + "." + month + "." + year;
    }


    /**
     * Gets an event with the information of a given document
     *
     * @param doc the document with the data to be added
     */
    public Event(Document doc) {
        ObjectId objectId = doc.getObjectId("_id");
        MongoCollection<Document> events = db.getEvents();
        this.id = objectId.toString();
        this.date = doc.getDate("date");
        this.description = doc.getString("description");

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        String year = Integer.toString(cal.get(YEAR));
        String month = Integer.toString(cal.get(MONTH) + 1);
        if (month.length() < 2) month = "0" + month;
        String day = Integer.toString(cal.get(DAY_OF_MONTH));
        if (day.length() < 2) day = "0" + day;

        dateString = day + "." + month + "." + year;
    }

    /**
     * Retrieves the event with the given id from the database
     *
     * @param id the unique identifier of the event
     */
    public Event(String id) {

        ObjectId objectId = new ObjectId(id);
        MongoCollection<Document> events = db.getEvents();
        this.id = id;
        this.date = events.find(new Document("_id", id)).first().getDate("date");
        this.description = events.find(new Document("_id", id)).first().getString("description");

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        String year = Integer.toString(cal.get(YEAR));
        String month = Integer.toString(cal.get(MONTH) + 1);
        if (month.length() < 2) month = "0" + month;
        String day = Integer.toString(cal.get(DAY_OF_MONTH));
        if (day.length() < 2) day = "0" + day;

        dateString = day + "." + month + "." + year;
    }

    /**
     * Gets the id of the event
     *
     * @return the id of the event
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the date of the event as a Date
     *
     * @return the date of the event
     */
    public Date getDate() {
        return date;
    }

    /**
     * Gets the date of the event as a String
     *
     * @return the date of the event as a String
     */
    public String getDateString() {
        return dateString;
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
     * Deletes the event of a given id String
     * @param id the id of the event
     */
    public static void removeEvent(String id) {
        ObjectId objectId = new ObjectId(id);
        db.getEvents().deleteOne(new Document("_id", objectId));
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
     * Gets an ArrayList of the next 'number' events, ordered by date, ascending
     *
     * @param number the number of events
     * @return the ArrayList
     */
    public static ArrayList<Event> getNextEvents(int number) {
        MongoCollection<Document> collection = db.getEvents();

        Bson filter = Filters.gte("date", new Date());
        Bson sort = Sorts.ascending("date");

        ArrayList<Document> documents = collection.find(filter).sort(sort).limit(number).into(new ArrayList<>());
        ArrayList<Event> events = new ArrayList<>(documents.size());
        documents.forEach(document -> events.add(new Event(document)));

        return events;
    }

    /**
     * Overloaded <code>getNextEvents(int number)</code>, returning an ArrayList of up to 10 documents
     *
     * @return the ArrayList of events
     */
    public static ArrayList<Event> getNextEvents() {
        return getNextEvents(10);
    }

}
