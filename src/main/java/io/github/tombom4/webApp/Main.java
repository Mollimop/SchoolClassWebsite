package io.github.tombom4.webApp;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.StringWriter;
import java.util.HashMap;

import static spark.Spark.*;

/**
 * @author Thomas
 * @version 1.0 09.01.2016
 */
public class Main {

/*    static Database db;
    static MongoCollection<BsonDocument> users;*/
    static final Configuration configuration = new Configuration();

    public static void main(String[] args) {
        /*db = new Database();
        users = db.getUsers();*/

        // Initialize Freemarker configuration
        configuration.setClassForTemplateLoading(Main.class, "/");

        staticFileLocation("/");
        get("/", (request, response) -> {
            StringWriter writer = new StringWriter();
            try {
                Template template = configuration.getTemplate("templates/index.ftl");
                template.process(new HashMap<String, Object>(), writer);
            } catch (Exception e) {
                e.printStackTrace();
                halt(500);
            }
            return writer;
        });
    }
}
