package com.jeyers.sstkit;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jspecify.annotations.NonNull;

import java.util.List;


public class ListCommand implements CommandExecutor {

    private final JavaPlugin plugin;
    public ListCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, String @NonNull [] args) {
        List<String> onlinePlayers = Bukkit.getOnlinePlayers().stream()
                .map(Player::getName)
                .toList();

        if (plugin.getConfig().getBoolean("playerlistCommandPermission.opOnly") && !sender.isOp()){
            sender.sendMessage("You do not have permission to use this command");
            return true;
        }

        String result = String.join(",\n   ", onlinePlayers);
        sender.sendMessage("Online Players:\n   " + result);

        return true;
    }

}
