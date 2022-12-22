package me.night.nullvalkyrie.database;

import com.mongodb.client.*;
import org.bson.Document;

public class DatabaseManager {
    public static MongoDatabase database;
    public void connect() {
        database = MongoClients.create(System.getenv("MONGODB_URI")).getDatabase("NullValkyrie");
    }
    public MongoCollection<Document> getMinersDB() {
        return database.getCollection("miners");
    }

    public MongoCollection<Document> getShopsDB() {
        return database.getCollection("shops");
    }

    public MongoCollection<Document> getRanksDB() {
        return database.getCollection("ranks");
    }

    public MongoCollection<Document> getNPCsDB() {
        return database.getCollection("npcs");
    }

    public MongoCollection<Document> getUsersDB() {
        return database.getCollection("users");
    }

    public MongoCollection<Document> getCustomWeaponsDB() {
        return database.getCollection("custom_weapons");
    }

}
