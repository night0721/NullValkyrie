package me.night.nullvalkyrie.database;

import com.mongodb.client.MongoCursor;
import org.bson.Document;

import java.util.HashMap;

public class ShopDataManager {
    public static HashMap<String, Integer> getItems() {
        HashMap<String, Integer> list = new HashMap<>();
        try (MongoCursor<Document> cursor = DatabaseManager.shops.find().cursor()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                list.put(doc.getString("Name"), doc.getInteger("Price"));
            }
            return list;
        }
    }
}
