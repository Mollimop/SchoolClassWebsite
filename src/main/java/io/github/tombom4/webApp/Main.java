package io.github.tombom4.webApp;

import com.mongodb.client.MongoCollection;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import io.github.tombom4.userManagement.Database;
import io.github.tombom4.userManagement.Session;
import io.github.tombom4.userManagement.User;
import org.bson.Document;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static freemarker.template.Configuration.VERSION_2_3_23;
import static spark.Spark.*;

/**
 * @author Thomas
 * @version 1.0 09.01.2016
 */
public class Main {

    static Database db;
    static MongoCollection<Document> users;
    static final Configuration configuration = new Configuration(VERSION_2_3_23);

    public static void main(String[] args) {

        try {
            db = new Database();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        users = db.getUsers();

        initializeClasses();

        // Initialize Freemarker configuration
        configuration.setClassForTemplateLoading(Main.class, "/");
        configuration.setDefaultEncoding("UTF-8");
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);

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

        post("/login", (request, response) -> {
            try {
                if (Session.checkSession(request) == null) {
                    new Session(request.queryParams("usr"), request.queryParams("psw"), response);
                }

                String redirect = request.headers("redirect");

                if (redirect != null) response.redirect("/" + redirect);
                else response.redirect("/");

            } catch (IOException e) {
                e.printStackTrace();
                response.redirect("/");
            }
            return "login problem";
        });

        // Configure get requests for index
        try {
            Template template = configuration.getTemplate("templates/index.ftl");
            getPage("index", template);
        } catch (IOException e) {
            System.out.println("index.ftl not found");
        } catch (Exception e) {
            System.out.println("An unexpected error (" + e.getMessage() + ") occurred while loading the index page");
            e.printStackTrace();
        }

        // Configure get requests for events
        try {
            Template template = configuration.getTemplate("templates/events.ftl");

            Map<String, Object> root = new HashMap<>(2);

            List<Event> events = Event.getNextEvents();

            root.put("events", events);
            root.put("test", "testValue");

            getPage("events", template, root);
        } catch (IOException e) {
            System.out.println("events.ftl not found");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("An unexpected error (" + e.getMessage() + ") occurred while loading the events page");
            e.printStackTrace();
        }

    }

    private static void getPage(String pageName, Template template, Map<String, Object> map) {
        get("/" + pageName, (request, response) -> {
            String user = Session.checkSession(request);
            try {
                if (user == null) {
                    response.header("redirect", pageName);
                    response.redirect("/");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            StringWriter writer = new StringWriter();
            map.put("username", user);
            template.process(map, writer);
            return writer;
        });
    }

    private static void getPage(String pageName, Template template) {
        getPage(pageName, template, new HashMap<>());
    }

    private static void initializeClasses() {
        Session.init(db);
        User.init(db);
        Event.init(db);
    }
}
