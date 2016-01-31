package io.github.tombom4.webApp;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import io.github.tombom4.userManagement.Database;
import io.github.tombom4.userManagement.Session;
import io.github.tombom4.userManagement.User;

import java.io.IOException;
import java.io.StringWriter;
import java.util.*;

import static freemarker.template.Configuration.VERSION_2_3_23;
import static spark.Spark.*;

/**
 * @author Thomas
 * @version 1.0 09.01.2016
 */
public class Main {

    static Database db;
    static final Configuration configuration = new Configuration(VERSION_2_3_23);

    public static void main(String[] args) {

        initializeClasses();

        // Initialize Freemarker configuration
        configuration.setClassForTemplateLoading(Main.class, "/");
        configuration.setDefaultEncoding("UTF-8");
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);

        port(80);
        externalStaticFileLocation("/var/www/public");
        get("/", (request, response) -> {
            if (Session.checkSession(request) != null) {
                if (request.headers("redirect") != null)
                    response.redirect("/" + request.headers("redirect"));
                else
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
        get("/index", (request, response) -> {

            try {

                Template template = configuration.getTemplate("templates/index.ftl");

                Map<String, Object> root = new HashMap<>();

                String user = Session.checkSession(request);

                if (user == null) {
                    response.header("redirect", "events");
                    response.redirect("/");
                    return "";
                }

                StringWriter writer = new StringWriter();
                root.put("username", user);
                template.process(root, writer);
                return writer;

            } catch (Exception e) {

                try {

                    Template template = configuration.getTemplate("templates/error.ftl");
                    StringWriter writer = new StringWriter();
                    Map<String, Object> root = new HashMap<>(3);
                    root.put("origin", "Startseite");
                    root.put("origin_path", "/index");
                    root.put("stacktrace", Arrays.toString(e.getStackTrace()));

                    template.process(root, writer);
                    return writer;

                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }

            return "An error occurred while loading events page. An error page could not be generated.";

        });


        // Configure get requests for events

        get("/events", (request, response) -> {

            try {

                Template template = configuration.getTemplate("templates/events.ftl");

                Map<String, Object> root = new HashMap<>(2);

                List<Event> events = Event.getNextEvents();

                root.put("events", events);
                root.put("test", "testValue");

                initializeClasses();
                String user = Session.checkSession(request);
                if (user == null) {
                    response.header("redirect", "events");
                    response.redirect("/");
                    return "";
                }

                StringWriter writer = new StringWriter();
                root.put("username", user);
                template.process(root, writer);
                return writer;

            } catch (Exception e) {

                try {

                    Template template = configuration.getTemplate("templates/error.ftl");
                    StringWriter writer = new StringWriter();
                    Map<String, Object> root = new HashMap<>(3);
                    root.put("origin", "Termine");
                    root.put("origin_path", "/events");
                    root.put("stacktrace", Arrays.toString(e.getStackTrace()));

                    template.process(root, writer);
                    return writer;

                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }

            return "An error occurred while loading events page. An error page could not be generated.";

        });


        post("addevent", (request, response) -> {
            String user = Session.checkSession(request);
            if (user != null) {
                try {
                    Event event = new Event(parseDate(request.queryParams("date")),
                            request.queryParams("description"));
                    response.redirect("/events");
                } catch (Exception e) {

                    try {

                        Template template = configuration.getTemplate("templates/error.ftl");
                        StringWriter writer = new StringWriter();
                        Map<String, Object> root = new HashMap<>(3);
                        root.put("origin", "Termine");
                        root.put("origin_path", "/events");
                        root.put("stacktrace", Arrays.toString(e.getStackTrace()));

                        template.process(root, writer);
                        return writer;

                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                }
            } else {
                response.redirect("/");
            }
            return "An error occurred while adding an event. An error page could not be generated.";
        });

    }

/*    private static void getPage(String path, String pageName, Template template, Map<String, Object> map) {
        get("/" + path, (request, response) -> {
            initializeClasses();
            String user = Session.checkSession(request);
            if (user == null) {
                response.header("redirect", path);
                response.redirect("/");
            }
            StringWriter writer = new StringWriter();
            map.put("username", user);
            template.process(map, writer);
            return writer;
        });
    }*/

/*    private static void getPage(String pageName, Template template) {
        getPage(pageName, template, new HashMap<>());
    }*/

    private static void initializeClasses() {

        try {
            db = new Database();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Session.init(db);
        User.init(db);
        Event.init(db);

    }

    private static Date parseDate(String string) throws IllegalArgumentException {
        String[] strings = string.split("\\.");
        if (strings.length == 3) {

            int day = Integer.parseInt(strings[0]);
            int month = Integer.parseInt(strings[1]) - 1;
            int year = Integer.parseInt(strings[2]);
            if (year < 100) year += 2000;
            if ((day > 0 && day <= 31) && (month > -1 && month <= 12)) {
                Calendar cal = Calendar.getInstance();
                cal.set(year, month, day);
                return cal.getTime();
            }

        }

        throw new IllegalArgumentException();
    }

}
