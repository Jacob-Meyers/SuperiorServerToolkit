package com.jeyers.sstkit;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jspecify.annotations.NonNull;

import java.util.HashMap;
import java.util.Map;

public class GhostCommand implements CommandExecutor {

    static Map<String, Location> ghostLocMap = new HashMap<>();
    static Map<String, GameMode> ghostGmMap = new HashMap<>();

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, String @NonNull [] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this command!");
            return true;
        }

        String playerName = player.getName();

        if (!ghostLocMap.containsKey(playerName)){
            ghostLocMap.put(playerName, player.getLocation());
            ghostGmMap.put(playerName, player.getGameMode());
            player.setGameMode(GameMode.SPECTATOR);
        } else {
            player.setGameMode(ghostGmMap.get(playerName));
            player.teleport(ghostLocMap.get(playerName));
            ghostLocMap.remove(playerName);
            ghostGmMap.remove(playerName);
        }

        return true;
    }

}
