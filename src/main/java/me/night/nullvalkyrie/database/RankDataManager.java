package me.night.nullvalkyrie.database;

import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import me.night.nullvalkyrie.enums.Rank;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

import static me.night.nullvalkyrie.ui.ScoreboardListener.nameTagManager;

public class RankDataManager {
    public static void setRank(UUID uuid, Rank rank) {
        Document document = DatabaseManager.getRanksDB().find(new Document("UUID", uuid.toString())).first();
        if(document != null) {
            Bson updated = new Document("Rank", rank.name());
            Bson update = new Document("$set", updated);
            DatabaseManager.getRanksDB().updateOne(document, update);
        } else {
            Document newDocument = new Document();
            newDocument.put("UUID", uuid.toString());
            newDocument.put("Rank", rank.name());
            DatabaseManager.getRanksDB().insertOne(newDocument);
        }
        for(Player player : Bukkit.getOnlinePlayers()) {
            if(player.hasPlayedBefore()) {
                nameTagManager.removeTag(player);
                nameTagManager.newTag(player);
            }
        }
    }
    public static Rank getRank(UUID uuid) {
        try (MongoCursor<Document> cursor = DatabaseManager.getRanksDB().find(Filters.eq("UUID", uuid.toString())).cursor()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                for (String key : doc.keySet()) {
                    if (key.equals("Rank")) {
                        return Rank.valueOf((String) doc.get(key));
                    }
                }
            }
        }
        return null;
    }
}
