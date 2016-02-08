package io.github.tombom4.userManagement;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.conversions.Bson;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.util.UUID;

/**
 * @author Thomas
 * @version 1.0 25.01.2016
 */
public class Session {

    private static Database db;

    public Session(String user, String psw, Response response) throws IOException {
        psw = new Password(psw).toString();

        if (!db.checkCredentials(user, psw)) {
            throw new IOException("Wrong credentials!");
        }

        String random = UUID.randomUUID().toString();

        Document session = new Document("_id", random)
                .append("user", user);

        response.cookie("session", random, 86400);

        db.getSessions().insertOne(session);
    }

    public static void init(Database db) {
        Session.db = db;
    }

    public static User checkSession (Request request) {
        MongoCollection<Document> sessions = Session.db.getSessions();
        String id = request.cookie("session");
        Bson filter = new Document("_id", id);

        if (sessions.count(filter) == 0) {
            return null;
        }

        return new User( sessions.find(filter).first().getString("user") );
    }

}
