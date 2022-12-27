package me.night.nullvalkyrie.database;

import com.mongodb.client.MongoCursor;
import me.night.nullvalkyrie.entities.miners.CryptoMiner;
import me.night.nullvalkyrie.entities.miners.MinerType;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.HashMap;

public class MinerDataManager {
    public static void setMiner(String name, MinerType type, int level, double rate, boolean enabled, long lastclaim, Location location) {
        Document newDocument = new Document();
        newDocument.put("ID", new DatabaseManager().getMinersDB().countDocuments() + 1);
        newDocument.put("Name", name);
        newDocument.put("Material", type.getName());
        newDocument.put("Level", level);
        newDocument.put("Rate", rate);
        newDocument.put("Enabled", enabled);
        newDocument.put("LastClaim", lastclaim);
        newDocument.put("x", location.getX());
        newDocument.put("y", location.getY());
        newDocument.put("z", location.getZ());
        new DatabaseManager().getMinersDB().insertOne(newDocument);
    }

    public static void setLastClaim(String name) {
        Document document = new DatabaseManager().getMinersDB().find(new Document("Name", name)).first();
        if (document != null) {
            Bson updated = new Document("LastClaim", System.currentTimeMillis());
            Bson update = new Document("$set", updated);
            new DatabaseManager().getMinersDB().updateOne(document, update);
        }
    }

    public static long getLastClaim(long id) {
        Document doc = new DatabaseManager().getMinersDB().find(new Document("ID", id)).first();
        if (doc != null) {
            for (String key : doc.keySet()) {
                if (key.equals("LastClaim")) return (long) doc.get(key);
            }
        }
        return 0;
    }

    public static HashMap<Long, CryptoMiner> getMiners() {
        HashMap<Long, CryptoMiner> list = new HashMap<>();
        try (MongoCursor<Document> cursor = new DatabaseManager().getMinersDB().find().cursor()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                list.put(doc.getLong("ID"), new CryptoMiner(doc.getString("Name"), MinerType.getByName(doc.getString("Material")), doc.getInteger("Level"), doc.getDouble("Rate"), doc.getLong("LastClaim"), new Location(Bukkit.getWorld("world"), doc.getDouble("x"), doc.getDouble("y"), doc.getDouble("z"))));
            }
            return list;
        }
    }

}

