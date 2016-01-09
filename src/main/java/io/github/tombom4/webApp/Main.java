package io.github.tombom4.webApp;

import com.mongodb.client.MongoCollection;
import freemarker.template.Configuration;
import freemarker.template.Template;
import io.github.tombom4.mongodb.Database;
import org.bson.BsonDocument;

import java.io.StringWriter;
import java.util.HashMap;

import static spark.Spark.*;

/**
 * @author Thomas
 * @version 1.0 09.01.2016
 */
public class Main {

    static Database db;
    static MongoCollection<BsonDocument> users;
    static final Configuration configuration = new Configuration();

    public static MongoCollection<BsonDocument> getUsers() {
        return users;
    }

    public static void main(String[] args) {
        db = new Database();
        users = db.getUsers();

        // Initialize Freemarker configuration
        configuration.setClassForTemplateLoading(Main.class, "/");

        staticFileLocation("/");
        get("/", (request, response) -> {
            if (db.checkCredentials(request) != null) {
                response.redirect("/index");
            }
            StringWriter writer = new StringWriter();
            try {
                Template template = configuration.getTemplate("templates/login.ftl");
                template.process(new HashMap<String, Object>(), writer);
            } catch (Exception e) {
                e.printStackTrace();
                halt(500);
            }
            return writer;
        });

        post("/index", (request, response) -> {
            StringWriter writer = new StringWriter();
            try {
                if (db.logIn(request.queryParams("usr"), request.queryParams("psw"), response) == null) {
                    halt(403);
                } else {
                    Template template = configuration.getTemplate("templates/index.ftl");
                    template.process(new HashMap<String, Object>(), writer);
                }
            } catch (IllegalArgumentException e) {
                halt(403, "Wrong credentials!");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return writer;
        });

        get("/index", (request, response) -> {
            StringWriter writer = new StringWriter();
            try {
                if (db.checkCredentials(request) == null) {
                    response.redirect("/");
                } else {
                    Template template = configuration.getTemplate("templates/index.ftl");
                    template.process(new HashMap<String, Object>(), writer);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return writer;
        });
    }
}
