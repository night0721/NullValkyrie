package me.night.nullvalkyrie.database;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mongodb.client.MongoCursor;
import me.night.nullvalkyrie.util.Util;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R1.CraftServer;
import org.bukkit.craftbukkit.v1_19_R1.CraftWorld;

import java.util.UUID;

import static me.night.nullvalkyrie.npc.NPCManager.*;


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
        try (MongoCursor<Document> cursor = DatabaseManager.getNPCsDB().find().cursor()) {
            while (cursor.hasNext()) {
                Document document = cursor.next();
                String name = document.getString("Name");
                int x = document.getInteger("x");
                int y = document.getInteger("y");
                int z = document.getInteger("z");
                int pitch = document.getInteger("pitch");
                int yaw = document.getInteger("yaw");
                String world = document.getString("world");
                String texture = document.getString("texture");
                String signature = document.getString("signature");
                Location location = new Location(Bukkit.getWorld(world), x, y, z);
                location.setPitch((float) pitch);
                location.setYaw((float) yaw);
                GameProfile gameProfile = new GameProfile(UUID.randomUUID(), Util.color(name));
                gameProfile.getProperties().put("textures", new Property("textures", texture, signature));
                MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
                WorldServer w = ((CraftWorld) location.getWorld()).getHandle();
                EntityPlayer npc = new EntityPlayer(server, w, gameProfile, null);
                npc.a(location.getX(), location.getY(), location.getZ(), location.getPitch(), location.getPitch());
                addNPCPacket(npc);
                getNPCs().add(npc);
            }
        }
    }
}