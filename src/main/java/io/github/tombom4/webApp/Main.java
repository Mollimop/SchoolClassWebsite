package io.github.tombom4.webApp;

import com.mongodb.client.MongoCollection;
import freemarker.template.Configuration;
import freemarker.template.Template;
import io.github.tombom4.userManagement.Database;
import io.github.tombom4.userManagement.Session;
import org.bson.Document;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;

import static spark.Spark.*;

/**
 * @author Thomas
 * @version 1.0 09.01.2016
 */
public class Main {

    static Database db;
    static MongoCollection<Document> users;
    static final Configuration configuration = new Configuration();

    public static void main(String[] args) {

        try {
            db = new Database();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        users = db.getUsers();

        Session.init(db);

        // Initialize Freemarker configuration
        configuration.setClassForTemplateLoading(Main.class, "/");

        staticFileLocation("/");
        get("/", (request, response) -> {
            if (Session.checkSession(request) != null) {
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

/*        post("/index", (request, response) -> {
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
        */

        get("/index", (request, response) -> {
            StringWriter writer = new StringWriter();
            try {
                if (Session.checkSession(request) == null) {
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

        post("/login", (request, response) -> {
            try {
                if (Session.checkSession(request) == null) {
                    new Session(request.queryParams("usr"), request.queryParams("psw"), response);
                }
                    response.redirect("/index");
            } catch (IOException e) {
                e.printStackTrace();
                response.redirect("/");
            }
            return "login problem";
        });

    }
}
