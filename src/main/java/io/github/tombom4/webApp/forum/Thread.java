package io.github.tombom4.webApp.forum;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.UpdateResult;
import io.github.tombom4.userManagement.Database;
import io.github.tombom4.userManagement.User;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

/**
* This class represents a forum thread in the SchoolClassWebsite project.
* Each thread has a title, a body, a subject, a questioner, an array of
* users that are permitted to view the thread an array of Answers and can
* be open ore closed. These values can be changed at any time.
* <p>
* Threads can be optionally stored in the MongoDB database and therefore
* a <code>final</code> id represented as a String.
*
* Schema for a JSON document in the database:
* {
*  "_id": id                   [ObjectId]
*  "questioner": questioner    [String]
*  "subject": schoolSubject    [String]
*  "title": title              [String]
*  "body": body                [String]
*  "visibleFor": visibleFor    [String[]]
*  "answers": answers          [Document[]]
*  "open": open                [boolean]
* }
*
* @author Sebastian Vogt
* @author Thomas Kirz
*/
public class Thread {

    /**
    * The database used for saving and retrieving the threads
    */
    private static Database db;


    /**
    * The unique identifier of the Thread
    */
    private final String id;

    /**
    * The title of the thread
    */
    private String title;

    /**
    * The subject to which the thread is related
    */
    private String schoolSubject;

    /**
    * The <code>User</code> that opened the thread
    */
    private User questioner;

    /**
    * The body/text of the thread
    */
    private String body;

    /**
    * An array of <code>User</code>s that are permitted to view the thread
    */
    private User[] visibleFor;

    /**
    * An ArrayList of <code>Answer</code>s
    */
    private ArrayList<Answer> answers = new ArrayList<>();

    /**
    * Specifies if the thread is open
    */
    private boolean open;

    /**
    * This constructor sets all fields of the thread and optionally stores the new thread in the database
    *
    * @param title         the title of the thread
    * @param schoolSubject the subject to which the thread is related
    * @param body          the body/text of the thread
    * @param questioner    the user that opened the thread
    * @param visibleFor    an array of users that are permitted to view the thread
    * @param answers       an ArrayList of answers of the thread
    * @param open          true if the thread is not closed yet
    * @param store         true if the thread should be stored in the database
    */
    public Thread(String title, String schoolSubject, String body, User questioner, User[] visibleFor, ArrayList<Answer> answers, boolean open, boolean store) {
        this.id = new ObjectId().toString();
        this.questioner = questioner;
        this.schoolSubject = schoolSubject;
        this.title = title;
        this.body = body;
        this.visibleFor = visibleFor;
        this.answers = answers;
        this.open = open;

        if (store) {

            MongoCollection<Document> threads = db.getThreads();

            Document thread = new Document("_id", new ObjectId(id))
            .append("questioner", questioner.getName())
            .append("subject", schoolSubject)
            .append("title", title)
            .append("body", body)
            .append("visibleFor", User.toStringArrayList(visibleFor))
            .append("answers", Answer.toDocumentArrayList(answers))
            .append("open", open);

            threads.insertOne(thread);

        }
    }

    /**
    * Generates a thread with a document from the database
    *
    * @param doc the <code>Document</code> to generate the thread from
    */

    public Thread(Document doc) throws NullPointerException {

        ArrayList<String> visibleForList = (ArrayList<String>) doc.get("visibleFor");
        ArrayList<Document> answerList = (ArrayList<Document>) doc.get("answers");

        this.id = doc.getObjectId("_id").toString();
        this.questioner = new User(doc.getString("questioner"));
        this.schoolSubject = doc.getString("subject");
        this.title = doc.getString("title");
        this.body = doc.getString("body");
        this.visibleFor = User.toUserArray(visibleForList);
        this.answers = Answer.toAnswerArrayList(answerList);
        this.open = doc.getBoolean("open");

    }

    /**
    * Gets the thread with the specified <code>id</code> from the database
    *
    * @param id the <code>id</code> of the thread
    */
    public Thread(String id) {

        MongoCollection<Document> threads = db.getThreads();
        Document doc = threads.find(new Document("_id", new ObjectId(id))).first();

        ArrayList<String> visibleForList;
        ArrayList<Document> answerList;

        if (doc.get("visibleFor") != null) {
            visibleForList = (ArrayList<String>) doc.get("visibleFor");
            this.visibleFor = User.toUserArray(visibleForList);
        } else {
            this.visibleFor = null;
        }

        if (doc.get("answers") != null) {
            answerList = (ArrayList<Document>) doc.get("answers");

            this.answers = Answer.toAnswerArrayList(answerList);
        } else {
            this.answers = null;
        }

        this.id = id;
        this.questioner = new User(doc.getString("questioner"));
        this.schoolSubject = doc.getString("subject");
        this.title = doc.getString("title");
        this.body = doc.getString("body");

    }

    /**
    * Gets an array of the next <code>number</code> threads, skipping
    * <code>offset</code> and sorted ascendingly by its title
    *
    * @param offset the number of threads to be skipped
    * @param number the total number of threads to be returned
    * @return the array of <code>number</code> threads
    */
    public static ArrayList<Thread> getNextThreads(int offset, int number) {

        try {
            MongoCollection<Document> collection = db.getThreads();

            Bson sort = Sorts.ascending("title");

            ArrayList<Document> docs = collection.find()
                .sort(sort).skip(offset).limit(number).into(new ArrayList<>());
            ArrayList<Thread> threads = new ArrayList<>();

            int counter = 0;

            for (Document doc : docs) {
                threads.add(new Thread(doc));
                counter++;
            }

            return threads;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ArrayList<Thread> getNextThreads() {
        return getNextThreads(0, 10);
    }

    /**
    * Adds an answer to the thread
    *
    * @param answer the new anser
    */
    public void addAnswer(Answer answer) {
        answers.add(answer);

        System.out.println("id in Thread:addAnswer(Answer answer): " + id);

        MongoCollection<Document> collection = db.getThreads();
        Bson filter = new Document("_id", new ObjectId(id));
        Bson update = new Document("$push",
            new Document("answers", answer.toDocument()));
        UpdateResult result = collection.updateOne(filter, update);

        System.out.println("wasAcknowledged: " + result.wasAcknowledged());
        if (result.wasAcknowledged()) {
            System.out.println("matchedCount: " + result.getMatchedCount());
            System.out.println("modifiedCount available: " + result.isModifiedCountAvailable());
            System.out.println("modifiedCount: " + result.getModifiedCount());
        }

    }

    /**
    * Initializes the static database used to store and retrieve the threads
    *
    * @param db the <code>Database</code>
    */
    public static void init(Database db) {
        Thread.db = db;
    }

    /**
    * Updates the title of the thread
    *
    * @param title the new title of the thread
    */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
    * Updates the body of the thread
    *
    * @param body the new body of the thread
    */
    public void setBody(String body) {
        this.body = body;
    }

    /**
    * Updates the array of users that are permitted to view the post
    *
    * @param visibleFor the new array of permitted users
    */
    public void setVisibleFor(User[] visibleFor) {
        this.visibleFor = visibleFor;
    }

    /**
    * Updates the value of the <code>open</code> boolean
    *
    * @param open the new value of <code>open</code>
    */
    public void setOpen(boolean open) {
        this.open = open;
    }

    /**
    * Gets the nth answer
    *
    * @param index the number of the answer (sorted, chronologically
    * ascending, starting with 0)
    * @return the corresponding <code>Answer</code> or <code>null</code> if it
    * doesn't exist
    */
    public Answer getAswerN(int index) {

        try {
            return answers.get(index);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }

    }

    public int getNumberOfAnswers() {
        return answers.size();
    }

    /**
    * Gets the title of the thread
    *
    * @return the title of the thread
    */
    public String getTitle() {
        return title;
    }

    /**
    * Gets the school subject that the thread is related to
    *
    * @return the subject as a <code>String</code>
    */
    public String getSchoolSubject() {
        return schoolSubject;
    }

    /**
    * Gets the <code>User</code> that opened the thread
    *
    * @return the <code>User</code> that opened the thread
    */
    public User getQuestioner() {
        return questioner;
    }

    /**
    * Gets the display name of the questioner
    *
    * @return the display name of the questioner
    */
    public String getQuestionerDisplayName() {
        return questioner.getDisplayName();
    }

    /**
    * Gets an <code>ArrayList</code> of answers
    *
    * @return the answers <code>ArrayList</code>
    */
    public ArrayList<Answer> getAnswers() {
        return answers;
    }

    /**
    * Gets the body of the thread
    *
    * @return the body of the thread as a String
    */
    public String getBody() {
        return body;
    }

    /**
    * Gets the <code>User</code>s to which the thread is visible
    *
    * @return the array of <code>User</code>s
    */
    public User[] getVisibleFor() {
        return visibleFor;
    }

    public String getId() {
        return id;
    }

    /**
    * Checks if the thread is open
    *
    * @return true if the thread is open, false if closed
    */
    public boolean isOpen() {
        return open;
    }


}
