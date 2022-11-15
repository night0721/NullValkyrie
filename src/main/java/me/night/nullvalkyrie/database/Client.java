package me.night.nullvalkyrie.database;

import com.mongodb.MongoException;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.HashMap;

public class Client {
    private MongoClient client;
    private static MongoCollection<Document> users;
    public Client() {
        connect();
    }
    public void connect() {
        try (MongoClient client = MongoClients.create("mongodb+srv://cath_exe:gaeismypassion@cath-exe.iolb7.mongodb.net/NullValkyrie")) {

        } catch (MongoException e) {
            System.out.println("An error occurred when logging in to MongoDB" + e);
        }
        MongoClient client = MongoClients.create("mongodb+srv://cath_exe:gaeismypassion@cath-exe.iolb7.mongodb.net/NullValkyrie");
        MongoDatabase database = client.getDatabase("NullValkyrie");
        users = database.getCollection("users");
    }
    public static void createUserSchema(String username) {
        Document document = new Document();
        document.put("Username", username);
        document.put("Bank", 0);
        users.insertOne(document);
    }
    public void updateUserBank(String username, Number coins) {
        Document document = (Document) users.find(new Document("Username", username)).first();
        if(document != null) {
            Bson updated = new Document("Bank", coins);
            Bson update = new Document("$set", updated);
            users.updateOne(document, update);
        }

    }
    public static HashMap<String, Object> getUser(String username) {
        try (MongoCursor<Document> cursor = users.find(Filters.eq("Username", username)).cursor()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                for (String a : doc.keySet()) {
                    if (!a.equals("_id")) {
                        HashMap<String, Object> details = new HashMap<>();
                        details.put(a, doc.get(a));
                        return details;
                    }
                }
            }
        }
        return null;
    }
}
