package io.github.tombom4.userManagement;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Thomas Kirz
 * @version 1.0 09.01.2016
 */
public class Database {

    private MongoClientURI mongoClientURI;
    private Properties properties;

    private MongoCollection<Document> users, sessions, events, threads, homework;

    /**
     * Gets the users collection
     *
     * @return the users collection
     */
    public MongoCollection<Document> getUsers() {
        return users;
    }


    /**
     * Gets the sessions collection
     *
     * @return the sessions collection
     */
    public MongoCollection<Document> getSessions() {
        return sessions;
    }

    /**
     * Gets the events collection
     *
     * @return the events collection
     */
    public MongoCollection<Document> getEvents() {
        return events;
    }

    /**
     * Gets the threads collection
     *
     * @return the threads collection
     */
    public MongoCollection<Document> getThreads() {
        return threads;
    }

    /**
     * Gets the threads collection
     *
     * @return the threads collection
     */
    public MongoCollection<Document> getHomework() {
        return homework;
    }

    /**
     * Initializes the database
     *
     * @throws IOException if the config.properties file is missing or not filled in completely
     */
    public Database() throws IOException {
        try {
            properties = new Properties();
            BufferedInputStream stream = new BufferedInputStream(new FileInputStream("resources/config.properties"));
            properties.load(stream);
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Problem with configuration file");
        }

        mongoClientURI = new MongoClientURI(properties.getProperty("mongoClientURI"));
        MongoClient client = new MongoClient(mongoClientURI);
        MongoDatabase db = client.getDatabase("schoolclasswebsite");

        users = db.getCollection("users", Document.class);
        sessions = db.getCollection("sessions", Document.class);
        events = db.getCollection("events", Document.class);
        threads = db.getCollection("threads", Document.class);
        homework = db.getCollection("homework", Document.class);
    }

    public boolean checkCredentials(String user, String psw) {
        Bson filter = new Document("_id", user);
        Document userDoc = users.find(filter).first();

        if (userDoc != null && userDoc.get("password").toString().equals(psw)) {
            return true;
        }
        return false;
    }
}
