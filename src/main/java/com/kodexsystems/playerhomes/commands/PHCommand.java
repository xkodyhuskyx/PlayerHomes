package com.kodexsystems.playerhomes.commands;

import com.kodexsystems.playerhomes.PlayerHomes;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;

public class PHCommand {

    public final PlayerHomes plugin;
    public final DateTimeFormatter dateformat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss.SSSS");

    public PHCommand(PlayerHomes plugin) {
        this.plugin = plugin;
    }

    /** Get the players remaining command cooldown in milliseconds.
     * @return Remaining cooldown in milliseconds
     */
    public int getCommandCooldown(Player player, String command) {
        if (player.hasPermission("playerhomes." + command + ".cooldown.bypass")) {
            return 0;
        }
        Integer cooldown = null;
        HashMap<String, Integer> groups = plugin.getPluginConfig().getCommandGroupCooldowns(command);
        if (groups != null) {
            for (String group : groups.keySet()) {
                int gcooldown = groups.get(group);
                if (player.hasPermission("playerhomes." + command + ".cooldown." + group) || group.equalsIgnoreCase("default")) {
                    if (gcooldown >= 0) {
                        cooldown = cooldown == null ? gcooldown : Math.min(cooldown, gcooldown);
                    } else {
                        plugin.showConsoleMessage(Level.SEVERE, command + ".invalid_cooldown", "group:" + group, "time:" + gcooldown);
                    }
                }
            }
        } else {
            plugin.showConsoleMessage(Level.SEVERE, "config_error." + command + "_no_cooldowns");
            return 0;
        }
        String lastusedtext = plugin.getPlayerData(player).getCommandLastUsed(command);
        if (lastusedtext != null) {
            try {
                LocalDateTime cooldownend = LocalDateTime.parse(lastusedtext, dateformat).plusSeconds(cooldown != null ? cooldown : 0);
                int until = Math.toIntExact((LocalDateTime.now()).until(cooldownend, ChronoUnit.MILLIS));
                return Math.max(until, 0);
            } catch (Exception e) {
                plugin.showConsoleMessage(Level.SEVERE, "user_error." + command + ".invalid_last_used", "date:" + lastusedtext);
                showCommandError(Level.SEVERE, player, "user_error." + command + ".invalid_last_used", "date:" + lastusedtext);
            }
        }
        return 0;
    }

    public void showCommandError(Level level, CommandSender sender, String key, String... args) {
        sender.sendMessage("Error: " + key + " " + Arrays.toString(args));
    }
}
