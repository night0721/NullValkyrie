package me.night.nullvalkyrie.database;

import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import me.night.nullvalkyrie.miners.CryptoMiner;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bukkit.Material;

import java.util.HashMap;

public class MinerDataManager {
    public static void setNPC(String name, Material material, int level, double rate, boolean enabled, long lastclaim) {
        Document newDocument = new Document();
        newDocument.put("ID", DatabaseManager.getMinersDB().countDocuments() + 1);
        newDocument.put("Name", name);
        newDocument.put("Material", material.name());
        newDocument.put("Level", level);
        newDocument.put("Rate", rate);
        newDocument.put("Enabled", enabled);
        newDocument.put("LastClaim", lastclaim);
        DatabaseManager.getMinersDB().insertOne(newDocument);
    }

    public static void setLastClaim(long id) {
        Document document = DatabaseManager.getMinersDB().find(new Document("ID", id)).first();
        if (document != null) {
            Bson updated = new Document("LastClaim", System.currentTimeMillis());
            Bson update = new Document("$set", updated);
            DatabaseManager.getMinersDB().updateOne(document, update);
        }
    }

    public static long getLastClaim(long id) {
        try (MongoCursor<Document> cursor = DatabaseManager.getMinersDB().find(Filters.eq("ID", id)).cursor()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                for (String key : doc.keySet()) {
                    if (key.equals("LastClaim")) return (long) doc.get(key);
                }
            }
        }
        return 0;
    }

    public static CryptoMiner getMiner(long id) {
        try (MongoCursor<Document> cursor = DatabaseManager.getMinersDB().find(Filters.eq("ID", id)).cursor()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                return new CryptoMiner(doc.getString("Name"), Material.matchMaterial(doc.getString("Material")), doc.getInteger("Level"), doc.getDouble("Rate"), doc.getLong("LastClaim"));
            }
        }
        return null;
    }

    public static HashMap<Long, CryptoMiner> getMiners() {
        HashMap<Long, CryptoMiner> list = new HashMap<>();
        try (MongoCursor<Document> cursor = DatabaseManager.getMinersDB().find().cursor()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                list.put(doc.getLong("ID"), new CryptoMiner(doc.getString("Name"), Material.matchMaterial(doc.getString("Material")), doc.getInteger("Level"), doc.getDouble("Rate"), doc.getLong("LastClaim")));
            }
            return list;
        }
    }

}

