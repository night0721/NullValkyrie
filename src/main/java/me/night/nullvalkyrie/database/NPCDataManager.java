package me.night.nullvalkyrie.database;

import com.mongodb.client.MongoCursor;
import me.night.nullvalkyrie.entities.npcs.NPCManager;
import org.bson.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class NPCDataManager {
    public static void setNPC(String name, int x, int y, int z, int pitch, int yaw, String world, String texture, String signature) {
        Document document = DatabaseManager.getNPCsDB().find(new Document("Name", name)).first();
        if (document != null) {
            System.out.println("A NPC with this name already exist");
        } else {
            Document newDocument = new Document();
            newDocument.put("Name", name);
            newDocument.put("x", x);
            newDocument.put("y", y);
            newDocument.put("z", z);
            newDocument.put("pitch", pitch);
            newDocument.put("yaw", yaw);
            newDocument.put("world", world);
            newDocument.put("texture", texture);
            newDocument.put("signature", signature);
            DatabaseManager.getNPCsDB().insertOne(newDocument);
        }
    }

    public static void reloadNPC() {
        List<HashMap<String, Object>> npcList = new ArrayList<>();
        try (MongoCursor<Document> cursor = DatabaseManager.getNPCsDB().find().cursor()) {
            while (cursor.hasNext()) {
                Document document = cursor.next();
                HashMap<String, Object> npc = new HashMap<>();
                String name = document.getString("Name");
                int x = document.getInteger("x");
                int y = document.getInteger("y");
                int z = document.getInteger("z");
                int pitch = document.getInteger("pitch");
                int yaw = document.getInteger("yaw");
                String world = document.getString("world");
                String texture = document.getString("texture");
                String signature = document.getString("signature");
                npc.put("name", name);
                npc.put("x", x);
                npc.put("y", y);
                npc.put("z", z);
                npc.put("pitch", pitch);
                npc.put("yaw", yaw);
                npc.put("world", world);
                npc.put("texture", texture);
                npc.put("signature", signature);
                npcList.add(npc);
            }
        }
        NPCManager.reloadNPC(npcList);
    }
}