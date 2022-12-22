package me.night.nullvalkyrie.database;

import com.mongodb.client.MongoCursor;
import me.night.nullvalkyrie.enums.Rank;
import me.night.nullvalkyrie.ui.player.ScoreboardListener;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class RankDataManager {
    public static void setRank(UUID uuid, Rank rank, ScoreboardListener... listener) {
        // TODO: fix not working in rank command
        Document document = new DatabaseManager().getRanksDB().find(new Document("UUID", uuid.toString())).first();
        if (document != null) {
            Bson updated = new Document("Rank", rank.name());
            Bson update = new Document("$set", updated);
            new DatabaseManager().getRanksDB().updateOne(document, update);
        } else {
            Document newDocument = new Document();
            newDocument.put("UUID", uuid.toString());
            newDocument.put("Rank", rank.name());
            new DatabaseManager().getRanksDB().insertOne(newDocument);
        }
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPlayedBefore()) {
                listener[0].nameTagManager.removeTag(player);
                listener[0].nameTagManager.newTag(player);
            }
        }
    }

    public static Rank getRank(UUID uuid) {
        try (MongoCursor<Document> cursor = new DatabaseManager().getRanksDB().find(new Document("UUID", uuid.toString())).cursor()) {
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
