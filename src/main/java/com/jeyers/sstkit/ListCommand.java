package com.jeyers.sstkit;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jspecify.annotations.NonNull;

import java.util.List;

///
/// Created by Jacob Meyers (TeamJEM)
/// File Created 3/18/2026
/// Last Edit    3/19/2026
///



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

        if (plugin.getConfig().getBoolean("playerlist.opOnlyPerm") && !sender.isOp()){
            sender.sendMessage("§cYou do not have permission to use this command");
            return true;
        }

        if (!onlinePlayers.isEmpty()) {
            String result = String.join(",\n   ", onlinePlayers);
            sender.sendMessage("§ePlayer Count:"+onlinePlayers.size()+"\n§bOnline Players:\n   " + result);
        } else
            sender.sendMessage("§cThere are no players online");


        return true;
    }

}
