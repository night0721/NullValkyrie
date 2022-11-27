package me.night.nullvalkyrie.database;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import me.night.nullvalkyrie.Main;
import me.night.nullvalkyrie.database.ranks.RankManager;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.HashMap;

public class DatabaseManager {
    private static MongoCollection<Document> users;
    private static MongoCollection<Document> custom_weapons;
    public static MongoCollection<Document> ranks;
    public MongoClient client;
    public static MongoDatabase database;
    private Main main;
    public DatabaseManager(Main main) {
        this.main = main;
        this.client = MongoClients.create(Main.env.get("MONGODB_URI"));
        database = client.getDatabase("NullValkyrie");
        users = database.getCollection("users");
        custom_weapons = database.getCollection("custom_weapons");
        ranks = database.getCollection("ranks");
    }
    public static void createUserSchema(String username) {
        Document document = new Document();
        document.put("Username", username);
        document.put("Bank", 0);
        users.insertOne(document);
    }
    public void updateUserBank(String username, Number coins) {
        Document document = users.find(new Document("Username", username)).first();
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
