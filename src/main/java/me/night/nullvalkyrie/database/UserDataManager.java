package me.night.nullvalkyrie.database;

import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.HashMap;

public class UserDataManager {
    public static void createUserSchema(String uuid) {
        Document document = new Document();
        document.put("UUID", uuid);
        document.put("Bank", 0);
        DatabaseManager.getUsersDB().insertOne(document);
    }

    public void updateUserBank(String uuid, Number coins) {
        Document document = DatabaseManager.getUsersDB().find(new Document("UUID", uuid)).first();
        if (document != null) {
            Bson updated = new Document("Bank", coins);
            Bson update = new Document("$set", updated);
            DatabaseManager.getUsersDB().updateOne(document, update);
        }
    }

    public static HashMap<String, Object> getUser(String uuid) {
        try (MongoCursor<Document> cursor = DatabaseManager.getUsersDB().find(Filters.eq("UUID", uuid)).cursor()) {
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
