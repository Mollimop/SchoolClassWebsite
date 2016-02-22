package io.github.tombom4.userManagement;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;

/**
 * User class, an implementation of a user of the website
 *
 * @author Thomas Kirz
 * @author Sebastian Vogt
 */

public class User {

    /**
     * The username of the user
     **/
    private String name;
    /**
     * The email-address of the user
     **/
    private String mail;
    /**
     * The encrypted password of the user
     **/
    private Password password;
    /**
     * The display name of the user
     **/
    private String displayName;
    /**
     * The type of the user (i.e. admin, student, teacher etc.)
     **/
    private String type;

    /**
     * The database in which the users session is stored
     **/
    private static Database db;

    /**
     * Creates a new instance of User and stores it in the sessions collection of the databases
     *
     * @param name     the name of the user
     * @param mail     the email address of the user
     * @param password the password of the user as a Password instance
     * @param type     the account type of the user
     */
    public User(String name, String mail, Password password, String displayName, String type) {
        this.name = name;
        this.mail = mail;
        this.password = password;
        this.displayName = displayName;
        this.type = type;
    }

    /**
     * Gets the user with the specified name from the database
     *
     * @param usr the username (represented as _id in the database) of the user
     */
    public User(String usr) throws NullPointerException {

            MongoCollection<Document> coll = db.getUsers();

            Bson filter = new Document("_id", usr);
            Document doc = coll.find(filter).first();

            this.name = usr;
            this.password = Password.byEncryptedPassword(doc.getString("password"));
            this.displayName = doc.getString("display_name");
            this.type = doc.getString("type");

    }

    public static void init(Database db) {
        User.db = db;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setPassword(Password password) {
        this.password = password;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        System.out.println(name);
        return name;
    }

    public String getMail() {
        return mail;
    }

    public Password getPassword() {
        return password;
    }

    public String getType() {
        return type;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static ArrayList<String> toStringArrayList(User[] users) {

        ArrayList<String> strings = new ArrayList<>(users.length);

        for (int i = 0; i < strings.size(); i++) {
            strings.set(i, users[i].toString());
        }

        return strings;

    }

    public static User[] toUserArray(ArrayList<String> strings) {
        User[] users = new User[strings.size()];

        for (int i = 0; i < strings.size(); i++) {
            users[i] = new User(strings.get(i));
        }

        return users;
    }
}
