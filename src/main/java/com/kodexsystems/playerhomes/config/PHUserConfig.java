package com.kodexsystems.playerhomes.config;

import com.kodexsystems.playerhomes.PlayerHomes;
import com.kodexsystems.playerhomes.objects.PHPlayer;
import org.bukkit.entity.Player;

public class PHUserConfig {

    private final PlayerHomes plugin;

    public PHUserConfig(PlayerHomes plugin) {
        this.plugin = plugin;
    }

    public PHPlayer getPlayerData(Player player) {
        return null;
    }
}
