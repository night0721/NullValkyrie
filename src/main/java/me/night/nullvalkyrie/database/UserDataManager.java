package me.night.nullvalkyrie.database;

import me.night.nullvalkyrie.ui.player.ScoreboardListener;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.HashMap;

public class UserDataManager {
    public static void createUserBank(String uuid) {
        Document document = new Document();
        document.put("UUID", uuid);
        document.put("Bank", 0);
        DatabaseManager.getUsersDB().insertOne(document);
    }

    public static void updateUserBank(String uuid, Integer coins) {
        Document document = DatabaseManager.getUsersDB().find(new Document("UUID", uuid)).first();
        if (document != null) {
            Integer coinsBefore = document.getInteger("Bank");
            Bson updated = new Document("Bank", coins + coinsBefore);
            Bson update = new Document("$set", updated);
            DatabaseManager.getUsersDB().updateOne(document, update);
            ScoreboardListener.sideBarManager.addBank(uuid, coins);
        } else {
            Document doc = new Document();
            doc.put("UUID", uuid);
            doc.put("Bank", coins);
            DatabaseManager.getUsersDB().insertOne(doc);
            ScoreboardListener.sideBarManager.addBank(uuid, coins);
        }


    }

    public static HashMap<String, Object> getUser(String uuid) {
        Document document = DatabaseManager.getUsersDB().find(new Document("UUID", uuid)).first();
        if (document != null) {
            HashMap<String, Object> map = new HashMap<>();
            for (String key : document.keySet()) map.put(key, document.get(key));
            map.remove("_id");
            return map;
        }
        return null;
    }
}
