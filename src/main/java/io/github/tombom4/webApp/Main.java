package io.github.tombom4.webApp;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import io.github.tombom4.userManagement.Database;
import io.github.tombom4.userManagement.Session;
import io.github.tombom4.userManagement.User;
import io.github.tombom4.webApp.forum.Thread;
import io.github.tombom4.webApp.forum.Answer;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URLEncoder;
import java.util.*;
import org.bson.types.ObjectId;
import static freemarker.template.Configuration.VERSION_2_3_23;
import static spark.Spark.*;

/**
* @author Thomas
* @version 1.0 09.01.16
*/
public class Main {

    static Database db;
    static final Configuration FREEMARKER_CONFIG = new Configuration(VERSION_2_3_23);

    public static void main(String[] args) {

        initializeClasses();

        // Initialize Freemarker configuration
        FREEMARKER_CONFIG.setClassForTemplateLoading(Main.class, "/");
        FREEMARKER_CONFIG.setDefaultEncoding("UTF-8");
        FREEMARKER_CONFIG.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);

        port(80);
        externalStaticFileLocation("/var/www/public");
        get("/", (req, res) -> {
            if (Session.checkSession(req) != null) {
                if (req.headers("redirect") != null)
                    res.redirect("/" + req.headers("redirect"));
                else
                    res.redirect("/index");
            }
            StringWriter writer = new StringWriter();
            try {
                Template template = FREEMARKER_CONFIG.getTemplate("templates/login.ftl");
                template.process(new HashMap<String, Object>(), writer);
            } catch (Exception e) {
                try {

                    Template template = FREEMARKER_CONFIG.getTemplate("templates/error.ftl");

                    String url = "/error?origin=Anmelden&path=/";
                    if (e.getMessage() != null)
                    url = url + "&stacktrace=" + URLEncoder.encode(Arrays.toString(e.getStackTrace()), "UTF-8");

                    res.redirect(url);

                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            return writer;
        });

        post("/login", (req, res) -> {
            try {
                if (Session.checkSession(req) == null) {
                    new Session(req.queryParams("usr"), req.queryParams("psw"), res);
                }

                String redirect = req.headers("redirect");

                if (redirect != null) res.redirect("/" + redirect);
                else res.redirect("/");

            } catch (IOException e) {

                try {

                    Template template = FREEMARKER_CONFIG.getTemplate("templates/error.ftl");

                    String url = "/error?origin=Anmeldung&path=/";
                    if (e.getMessage() != null)
                    url = url + "&stacktrace=" + URLEncoder.encode(Arrays.toString(e.getStackTrace()), "UTF-8");

                    res.redirect(url);

                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            return "login problem";
        });


        // Configure get requests for index
        get("/index", (req, res) -> {

            try {

                Template template = FREEMARKER_CONFIG.getTemplate("templates/index.ftl");

                Map<String, Object> root = new HashMap<>();

                User user = Session.checkSession(req);

                if (user == null) {
                    res.header("redirect", "events");
                    res.redirect("/");
                    return "";
                }

                StringWriter writer = new StringWriter();
                root.put("user", user);
                template.process(root, writer);
                return writer;

            } catch (Exception e) {

                try {

                    Template template = FREEMARKER_CONFIG.getTemplate("templates/error.ftl");

                    String url = "/error?origin=Startseite&path=/index";
                    if (e.getMessage() != null)
                    url = url + "&stacktrace=" + URLEncoder.encode(Arrays.toString(e.getStackTrace()), "UTF-8");

                    res.redirect(url);

                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }

            return "An error occurred while loading events page. An error page could not be generated.";

        });

        post("/events/add", (req, res) -> {
            User user = Session.checkSession(req);
            if (user != null) {

                try {

                    String param = req.params("param");

                    new Event(parseDate(req.queryParams("date")),
                    req.queryParams("description"), true);

                    res.redirect("/events");

                } catch (IllegalArgumentException e) {
                    res.redirect("/events/malformed");
                } catch (Exception e) {

                    try {

                        Template template = FREEMARKER_CONFIG.getTemplate("templates/error.ftl");

                        String url = "/error?origin=Termin+hinzufuegen&path=/events";
                        if (e.getMessage() != null)
                        url = url + "&stacktrace=" + URLEncoder.encode(Arrays.toString(e.getStackTrace()), "UTF-8");

                        res.redirect(url);

                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                }
            } else {
                res.redirect("/");
            }
            return "An error occurred while adding an event. An error page could not be generated.";
        });

        get("/events/remove/:id", (req, res) -> {
            User user = Session.checkSession(req);
            if (user != null) {

                try {

                    String id = req.params("id");

                    Event.removeEvent(id);

                    res.redirect("/events");

                } catch (IllegalArgumentException e) {
                    res.redirect("/events/malformed");
                } catch (Exception e) {

                    try {

                        Template template = FREEMARKER_CONFIG.getTemplate("templates/error.ftl");

                        String url = "/error?origin=Termin+entfernen&path=/events";
                        if (e.getMessage() != null)
                        url = url + "&stacktrace=" + URLEncoder.encode(Arrays.toString(e.getStackTrace()), "UTF-8");

                        res.redirect(url);

                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                }
            } else {
                res.redirect("/");
            }
            return "An error occurred while adding an event. An error page could not be generated.";
        });

        get("/events/malformed", (req, res) -> {

            try {

                Template template = FREEMARKER_CONFIG.getTemplate("templates/events.ftl");

                Map<String, Object> root = new HashMap<>(2);

                initializeClasses();

                List<Event> events = Event.getNextEvents();


                root.put("malformed", true);
                root.put("events", events);
                root.put("test", "testValue");

                User user = Session.checkSession(req);
                if (user == null) {
                    res.header("redirect", "events");
                    res.redirect("/");
                    return "";
                }

                StringWriter writer = new StringWriter();
                root.put("user", user);
                template.process(root, writer);
                return writer;

            } catch (Exception e) {

                try {

                    Template template = FREEMARKER_CONFIG.getTemplate("templates/error.ftl");

                    String url = "/error?origin=Termin+hinzufuegen&path=/events/malformed";
                    if (e.getMessage() != null)
                    url = url + "&stacktrace=" + URLEncoder.encode(Arrays.toString(e.getStackTrace()), "UTF-8");

                    res.redirect(url);

                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }

            return "An error occurred while loading events page. An error page could not be generated.";

        });

        // Configure get requests for events

        get("/events", (req, res) -> {

            try {

                Template template = FREEMARKER_CONFIG.getTemplate("templates/events.ftl");

                Map<String, Object> root = new HashMap<>(2);

                initializeClasses();

                List<Event> events = Event.getNextEvents();

                root.put("events", events);
                if (Objects.equals(req.params("param"), "malformed")) root.put("malformed", true);

                User user = Session.checkSession(req);
                if (user == null) {
                    res.header("redirect", "events");
                    res.redirect("/");
                    return "";
                }

                StringWriter writer = new StringWriter();
                root.put("user", user);
                template.process(root, writer);
                return writer;

            } catch (Exception e) {

                try {

                    Template template = FREEMARKER_CONFIG.getTemplate("templates/error.ftl");

                    String url = "/error?origin=Termine&path=/events";
                    if (e.getMessage() != null)
                    url = url + "&stacktrace=" + URLEncoder.encode(Arrays.toString(e.getStackTrace()), "UTF-8");

                    res.redirect(url);

                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }

            return "An error occurred while loading events page. An error page could not be generated.";

        });

        get("/error", (req, res) -> {

            Template template = FREEMARKER_CONFIG.getTemplate("templates/error.ftl");

            Map<String, Object> root = new HashMap<String, Object>(3);
            root.put("origin", req.queryParams("origin"));
            root.put("path", req.queryParams("path"));
            root.put("stacktrace", req.queryParams("stacktrace"));

            StringWriter writer = new StringWriter();

            template.process(root, writer);
            return writer;

        });

        get("/events", (req, res) -> {

            try {

                Template template = FREEMARKER_CONFIG.getTemplate("templates/events.ftl");

                Map<String, Object> root = new HashMap<>(2);

                initializeClasses();

                List<Event> events = Event.getNextEvents();

                root.put("events", events);
                if (Objects.equals(req.params("param"), "malformed")) root.put("malformed", true);

                User user = Session.checkSession(req);
                if (user == null) {
                    res.header("redirect", "events");
                    res.redirect("/");
                    return "";
                }

                StringWriter writer = new StringWriter();
                root.put("user", user);
                template.process(root, writer);
                return writer;

            } catch (Exception e) {

                try {

                    Template template = FREEMARKER_CONFIG.getTemplate("templates/error.ftl");

                    String url = "/error?origin=Termine&path=/events";
                    if (e.getMessage() != null)
                    url = url + "&stacktrace=" + URLEncoder.encode(Arrays.toString(e.getStackTrace()), "UTF-8");

                    res.redirect(url);

                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }

            return "An error occurred while loading events page. An error page could not be generated.";

        });

        get("/forum", (req, res) -> {
            try {

                Template template = FREEMARKER_CONFIG.getTemplate("templates/forum.ftl");

                initializeClasses();

                User user = Session.checkSession(req);
                if (user == null) {
                    res.header("redirect", "forum");
                    res.redirect("/");
                    return "";
                }

                Map<String, Object> root = new HashMap<>();
                root.put("user", user);
                root.put("threads", Thread.getNextThreads());

                if (req.queryParams("malformed") != null && req.queryParams("malformed") == "true")
                    root.put("malformed", true);

                StringWriter writer = new StringWriter();
                template.process(root, writer);
                return writer;

            } catch (Exception e) {

                try {

                    Template template = FREEMARKER_CONFIG.getTemplate("templates/error.ftl");

                    String url = "/error?origin=Forum&path=/forum";
                    if (e.getMessage() != null && e.getMessage() != "")
                    url = url + "&stacktrace=" + URLEncoder.encode(Arrays.toString(e.getStackTrace()), "UTF-8");

                    res.redirect(url);

                } catch (IOException e1) {
                    e1.printStackTrace();
                    return "";
                }

            }
            return "An error occurred while loading thread. Couldn't redirect "
            + "to error page.";

        });

        get("/forum/:thread", (req, res) -> {

            try {

                Template template = FREEMARKER_CONFIG.getTemplate("templates/thread.ftl");

                initializeClasses();

                User user = Session.checkSession(req);

                if (user == null) {
                    res.header("redirect", "forum");
                    res.redirect("/");
                    return null;
                }

                Map<String, Object> root = new HashMap<>(2);
                root.put("user", user);
                root.put("thread", new Thread(req.params("thread")));

                StringWriter writer = new StringWriter();
                template.process(root, writer);
                return writer;

            } catch(Exception e) {
                try {

                    Template template = FREEMARKER_CONFIG.getTemplate("templates/error.ftl");

                    String url = "/error?origin=Forum&path=/forum";
                    if (e.getMessage() != null && e.getMessage() != "");
                    url = url + "&stacktrace=" + URLEncoder.encode(Arrays.toString(e.getStackTrace()), "UTF-8");
                    e.printStackTrace();

                    res.redirect(url);

                } catch (IOException e1) {
                    e1.printStackTrace();
                    return "";
                }

            }

            return "An error occurred while loading thread. Couldn't redirect "
            + "to error page.";

        });

        post("/forum/add", (req, res) -> {

            try {
                User user = Session.checkSession(req);
                if (user == null) {
                    res.header("redirect", "forum");
                    res.redirect("/");
                    return null;
                }

                String title    = req.queryParams("title");
                String body     = req.queryParams("body");

                Thread thread = new Thread(title, "", body, user, new User[1],
                    new ArrayList<Answer>(), true, true);

                res.redirect("/forum");

                return "";

            } catch(Exception e) {
                res.redirect("/forum?malformed=true");
                e.printStackTrace();
            }

            return "";
        });

        post("/forum/:thread/answer", (req, res) -> {
            String id = req.params("thread");

            try {
                User user = Session.checkSession(req);
                if (user == null) {
                    res.header("redirect", "forum");
                    res.redirect("/");
                    return null;
                }

                String title    = req.queryParams("title");
                String body     = req.queryParams("body");

                Thread thread = new Thread(id);
                thread.addAnswer(new Answer(title, user, body, 0));

                res.redirect("/forum/" + id);

            } catch(Exception e) {
                res.redirect("/forum/" + id + "?malformed=true");
                e.printStackTrace();
            }

            return "";
        });
        
    }

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
        Thread.init(db);
        Homework.init(db);

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
