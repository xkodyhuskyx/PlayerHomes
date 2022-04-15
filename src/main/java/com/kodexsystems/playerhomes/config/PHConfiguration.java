package com.kodexsystems.playerhomes.config;

import com.kodexsystems.playerhomes.PlayerHomes;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.logging.Level;

public class PHConfiguration {

    private final PlayerHomes plugin;
    private FileConfiguration config;
    HashMap<String, Integer> homecooldowns = new HashMap<>();

    /** Initializes a new PHConfiguration class.
     * @param plugin PlayerHomes plugin instance.
     */
    public PHConfiguration(PlayerHomes plugin) {
        this.plugin = plugin;
    }

    public void load() {
        config = plugin.getConfig();
        homecooldowns = loadCommandGroupCooldowns("home");

    }

    /** Gets all loaded group cooldowns for the specified command.
     * @param command Command to get the cooldowns for.
     * @return List of all loaded cooldown groups with values.
     */
    public HashMap<String, Integer> getCommandGroupCooldowns(String command) {
        return homecooldowns;
    }

    /** Loads all configuration specified group cooldowns into memory
     * for the specified command.
     * @param command Command to get the cooldowns for.
     * @return List of all cooldown groups with values.
     */
    private HashMap<String, Integer> loadCommandGroupCooldowns(String command) {
        ConfigurationSection section = config.getConfigurationSection("commands." + command + ".cooldowns");
        if (section != null) {
            HashMap<String, Integer> cooldowns = new HashMap<>();
            for (String group : section.getKeys(false)) {
                int cooldown = section.getInt(group);
                if (!group.equals("bypass")) {
                    if (cooldown >= 0) {
                        cooldowns.put(group, cooldown);
                    } else {
                        plugin.showConsoleMessage(Level.SEVERE, "config_error." + command + "_group_invalid_cooldown", "group:" + group);
                    }
                } else {
                    plugin.showConsoleMessage(Level.SEVERE, "config_error." + command + "_group_invalid_name", "group:" + group);
                }
            }
            if (!cooldowns.isEmpty()) {
                homecooldowns = cooldowns;
                return cooldowns;
            } else {
                plugin.showConsoleMessage(Level.SEVERE, "config_error." + command + "_group_no_valid_cooldowns");
            }
        } else {
            plugin.showConsoleMessage(Level.SEVERE, "config_error." + command + "_no_cooldowns");
        }
        return null;
    }
}
