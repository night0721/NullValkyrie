package me.night.nullvalkyrie.database;

import com.mongodb.client.*;
import me.night.nullvalkyrie.Main;
import org.bson.Document;

public class DatabaseManager {
    public static MongoDatabase database;
    public DatabaseManager() {
        database = MongoClients.create(Main.env.get("MONGODB_URI")).getDatabase("NullValkyrie");
    }
    public static MongoCollection<Document> getMinersDB() {
        return database.getCollection("miners");
    }
    public static MongoCollection<Document> getShopsDB() {
        return database.getCollection("shops");
    }
    public static MongoCollection<Document> getRanksDB() {
        return database.getCollection("ranks");
    }
    public static MongoCollection<Document> getNPCsDB() {
        return database.getCollection("npcs");
    }
    public static MongoCollection<Document> getUsersDB() {
        return database.getCollection("users");
    }
    public static MongoCollection<Document> getCustomWeaponsDB() {
        return database.getCollection("custom_weapons");
    }
}
