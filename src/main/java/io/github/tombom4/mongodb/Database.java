package io.github.tombom4.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import io.github.tombom4.webApp.Main;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.conversions.Bson;
import spark.Request;
import spark.Response;
import spark.Spark;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Thomas Kirz
 * @version 1.0 09.01.2016
 */
public class Database {

    MongoClientURI mongoClientURI;
    Properties properties;
    MongoCollection<BsonDocument> users;

    /**
     * Returns the users collection
     * @return the users collection
     */
    public MongoCollection<BsonDocument> getUsers() {
        return users;
    }

    public Database() {
        try {
            properties = new Properties();
            BufferedInputStream stream = new BufferedInputStream(new FileInputStream("resources/config.properties"));
            properties.load(stream);
            stream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Spark.halt(500, "MongoDB property file missing");
        } catch (IOException e) {
            e.printStackTrace();
            Spark.halt(500);
        }

        mongoClientURI = new MongoClientURI(properties.getProperty("mongoClientURI"));
        MongoClient client = new MongoClient(mongoClientURI);
        MongoDatabase db = client.getDatabase("schoolclasswebsite");
        users = db.getCollection("users", BsonDocument.class);
    }

    public String logIn(String usr, String psw, Response response) throws IllegalArgumentException {
        Bson filter = new Document("_id", usr);
        BsonDocument user = Main.getUsers().find(filter).first();
        if (user.get("password").asString().getValue() != null &&
                user.get("password").asString().getValue().equals(psw)) {
            try {
                response.cookie("psw", psw, Integer.parseInt(properties.getProperty("cookieMaxAge")));
                response.cookie("usr", usr, Integer.parseInt(properties.getProperty("cookieMaxAge")));
            } catch (NullPointerException | NumberFormatException e) {
                e.printStackTrace();
                Spark.halt(500, "cookieMaxAge in config.properties is incorrect or missing");
            }
            return user.get("type").toString();
        } else {
            throw new IllegalArgumentException();
        }
    }

    public String checkCredentials(Request request) {
        if (request.cookie("usr") != null && request.cookie("psw") != null) {
            String usr = request.cookie("usr");
            String psw = request.cookie("psw");
            MongoCollection users = Main.getUsers();
            Bson filter = new Document("_id", usr);
            BsonDocument user = Main.getUsers().find(filter).first();
            if (user.get("_id") != null &&
                    user.get("_id").asString().getValue().equals(usr) &&
                    user.get("password") != null &&
                    user.get("password").asString().getValue().equals(psw)) {
                return user.get("type").toString();
            }
        }
        return null;
    }
}

