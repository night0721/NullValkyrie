package me.night.nullvalkyrie.database;

import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CustomWeaponsDataManager {

    public static HashMap<String, Object> getWeapon(String itemName) {
        HashMap<String, Object> item = new HashMap<>();
        try (MongoCursor<Document> cursor = DatabaseManager.getCustomWeaponsDB().find(Filters.eq("Name", itemName)).cursor()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                String name = doc.getString("Name");
                Document lore = (Document) doc.get("Lore");
                Document ability = (Document) lore.get("Ability");
                Document properties = (Document) lore.get("Properties");
                HashMap<String, HashMap<String, Object>> lores = new HashMap<>();
                HashMap<String, Object> abi = new HashMap<>();
                HashMap<String, Object> prop = new HashMap<>();
                abi.put("Name", ability.getString("Name"));
                List<String> details = new ArrayList<>();
                if (ability.get("Details") != null)
                    details.addAll((List<String>) ability.get("Details"));
                abi.put("Details", details);
                for (String a : properties.keySet()) prop.put(a, properties.get(a));
                lores.put("Ability", abi);
                lores.put("Properties", prop);
                Document enchants = (Document) doc.get("Enchants");
                Document attributes = (Document) doc.get("Attributes");
                HashMap<String, Object> ench = new HashMap<>();
                HashMap<String, Object> attr = new HashMap<>();
                for (String a : enchants.keySet()) ench.put(a, enchants.get(a));
                for (String a : attributes.keySet()) attr.put(a, attributes.get(a));
                Document pdc = (Document) doc.get("PDC");
                HashMap<String, Object> pdcdata = new HashMap<>();
                if (pdc != null)
                    for (String a : pdc.keySet()) pdcdata.put(a, pdc.get(a));
                Document recipe = (Document) doc.get("Recipes");
                HashMap<String, Object> recipes = new HashMap<>();
                if (recipe != null) {
                    Document ing = (Document) recipe.get("Ingredients");
                    HashMap<String, String> ingredients = new HashMap<>();
                    for (String i : ing.keySet())
                        ingredients.put(i, ing.getString(i));
                    List<String> shapes = new ArrayList<>();
                    if (recipe.get("Shapes") != null) shapes.addAll((List<String>) recipe.get("Shapes"));
                    recipes.put("Shape", shapes);
                    recipes.put("Amount", recipe.getInteger("Amount"));
                    recipes.put("Ingredients", ingredients);
                }
                item.put("Name", name);
                item.put("Material", Material.matchMaterial(doc.getString("Material")));
                item.put("Type", doc.getString("Type"));
                item.put("Rarity", doc.getString("Rarity"));
                item.put("Lore", lores);
                item.put("Enchants", ench);
                item.put("Attributes", attr);
                item.put("PDC", pdcdata);
                item.put("Recipes", recipes);
            }
            return item;
        }
    }

    public static HashMap<String, Object> getWeapons() {
        HashMap<String, Object> list = new HashMap<>();
        try (MongoCursor<Document> cursor = DatabaseManager.getCustomWeaponsDB().find().cursor()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                HashMap<String, Object> item = new HashMap<>();
                String name = doc.getString("Name");
                Document lore = (Document) doc.get("Lore");
                Document ability = (Document) lore.get("Ability");
                Document properties = (Document) lore.get("Properties");
                HashMap<String, HashMap<String, Object>> lores = new HashMap<>();
                HashMap<String, Object> abi = new HashMap<>();
                HashMap<String, Object> prop = new HashMap<>();
                abi.put("Name", ability.getString("Name"));
                List<String> details = new ArrayList<>();
                if (ability.get("Details") != null)
                    details.addAll((List<String>) ability.get("Details"));
                abi.put("Details", details);
                for (String a : properties.keySet()) prop.put(a, properties.get(a));
                lores.put("Ability", abi);
                lores.put("Properties", prop);
                Document enchants = (Document) doc.get("Enchants");
                Document attributes = (Document) doc.get("Attributes");
                HashMap<String, Object> ench = new HashMap<>();
                HashMap<String, Object> attr = new HashMap<>();
                for (String a : enchants.keySet()) ench.put(a, enchants.get(a));
                for (String a : attributes.keySet()) attr.put(a, attributes.get(a));
                item.put("Name", name);
                item.put("Material", Material.matchMaterial(doc.getString("Material")));
                item.put("Type", doc.getString("Type"));
                item.put("Rarity", doc.getString("Rarity"));
                item.put("Lore", lores);
                item.put("Enchants", ench);
                item.put("Attributes", attr);
                list.put(name, item);
            }
            return list;
        }
    }
}
