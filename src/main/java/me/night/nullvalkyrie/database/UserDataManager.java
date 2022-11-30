package me.night.nullvalkyrie.database;

import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.HashMap;

public class UserDataManager {
    public static void createUserSchema(String username) {
        Document document = new Document();
        document.put("Username", username);
        document.put("Bank", 0);
        DatabaseManager.users.insertOne(document);
    }
    public void updateUserBank(String username, Number coins) {
        Document document = DatabaseManager.users.find(new Document("Username", username)).first();
        if (document != null) {
            Bson updated = new Document("Bank", coins);
            Bson update = new Document("$set", updated);
            DatabaseManager.users.updateOne(document, update);
        }
    }

    public static HashMap<String, Object> getUser(String username) {
        try (MongoCursor<Document> cursor = DatabaseManager.users.find(Filters.eq("Username", username)).cursor()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                HashMap<String, Object> map = new HashMap<>();
                for (String key : doc.keySet()) map.put(key, doc.get(key));
                map.remove("_id");
                return map;
            }
        }
        return null;
    }
}
