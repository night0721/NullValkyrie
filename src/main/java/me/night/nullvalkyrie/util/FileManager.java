package me.night.nullvalkyrie.util;

import static me.night.nullvalkyrie.entities.items.CustomItemManager.updateYamlFilesToPlugin;

public class FileManager {
    public FileManager() {
        updateYamlFilesToPlugin(".env");
    }
}
