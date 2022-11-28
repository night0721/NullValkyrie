package me.night.nullvalkyrie.database;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bukkit.Material;

public class MinerDataManager {
    public static void setNPC(String name, Material material, int level, double rate, boolean enabled, long lastclaim) {
        Document newDocument = new Document();
        newDocument.put("ID", DatabaseManager.miners.countDocuments() + 1);
        newDocument.put("Name", name);
        newDocument.put("Material", material.name());
        newDocument.put("Level", level);
        newDocument.put("Rate", rate);
        newDocument.put("Enabled", enabled);
        newDocument.put("LastClaim", lastclaim);
        DatabaseManager.miners.insertOne(newDocument);
    }
    public static void setLastclaim(int id) {
        Document document = DatabaseManager.miners.find(new Document("ID", id)).first();
        if (document != null) {
            Bson updated = new Document("LastClaim", System.currentTimeMillis());
            Bson update = new Document("$set", updated);
            DatabaseManager.miners.updateOne(document, update);
        }
    }
}

