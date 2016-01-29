package io.github.tombom4.userManagement;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.conversions.Bson;

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
    public User(String usr) {
        MongoCollection<Document> coll = db.getUsers();

        Bson filter = new Document("_id", usr);
        Document doc = coll.find(filter).first();

        String mail = doc.getString("name");
        Password password = Password.byEncryptedPassword(doc.getString("password"));
        String displayName = doc.getString("display_name");
        String type = doc.getString("type");
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
}
