package net.tree.budgiesets.managers;

import org.bukkit.configuration.file.FileConfiguration;

public class MessageManager {

    //TODO: Add all messages and colour support to these messages. This will include internal Placeholder support for <ArmorSetName>

    public String EquipMessage;
    public String UnequipMessage;

    public MessageManager(ConfigurationManager configurationManager) {
        FileConfiguration config = configurationManager.getConfig("config.yml");
    }
}
