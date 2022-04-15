package com.kodexsystems.playerhomes;

import com.kodexsystems.playerhomes.config.PHI18n;
import com.kodexsystems.playerhomes.config.PHConfiguration;
import com.kodexsystems.playerhomes.config.PHUserConfig;
import com.kodexsystems.playerhomes.objects.PHPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ResourceBundle;
import java.util.logging.Level;

public final class PlayerHomes extends JavaPlugin {

    PHI18n i18n;
    PHConfiguration config;
    PHUserConfig users;

    @Override
    public void onEnable() {
        i18n = new PHI18n(this);
        config = new PHConfiguration(this);
        config.load();
        users = new PHUserConfig(this);

    }

    public PHI18n getPluginI18n() {return i18n;}
    public PHConfiguration getPluginConfig() {return config;}
    public PHUserConfig getUserConfig() { return users;}

    public void showConsoleMessage(Level level, String key, String... args) {
        String message = i18n.getStringOrDefault(key);
        if (args != null) {
            for (String arg : args) {
                String[] data = arg.split(":", 2);
                message.replace("%" + data[0] + "%", args[1]);
            }
        }

        if (level == Level.SEVERE) {

        }
    }

    @Override
    public void onDisable() {

    }

    public PHPlayer getPlayerData(Player player) {
        return users.getPlayerData(player);
    }
}
