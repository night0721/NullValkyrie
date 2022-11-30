package me.night.nullvalkyrie.database;

import com.mongodb.client.*;
import me.night.nullvalkyrie.Main;
import org.bson.Document;

public class DatabaseManager {
    public static MongoCollection<Document> users;
    public static MongoCollection<Document> custom_weapons;
    public static MongoCollection<Document> ranks;
    public static MongoCollection<Document> npcs;
    public static MongoCollection<Document> miners;
    public static MongoCollection<Document> shops;
    public static MongoDatabase database;

    public DatabaseManager() {
        database = MongoClients.create(Main.env.get("MONGODB_URI")).getDatabase("NullValkyrie");
        users = database.getCollection("users");
        custom_weapons = database.getCollection("custom_weapons");
        ranks = database.getCollection("ranks");
        npcs = database.getCollection("npcs");
        miners = database.getCollection("miners");
        shops = database.getCollection("shops");
    }
}
