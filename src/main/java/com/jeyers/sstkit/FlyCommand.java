package com.jeyers.sstkit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NonNull;

///
/// Created by Jacob Meyers (TeamJEM)
/// File Created 3/17/2026
/// Last Edit    3/19/2026
///


public class FlyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, String @NonNull [] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cThis command can only be used by players.");
            return true;
        }

        if (!player.hasPermission("sstkit.permission.fly")) {
            player.sendMessage("§cYou do not have permission for that command.");
            return true;
        }

        // Toggle flight mode
        if (player.getAllowFlight()) {
            player.setAllowFlight(false);
            player.setFlying(false);
            player.sendMessage("§cFlight mode disabled.");
        } else {
            player.setAllowFlight(true);
            player.sendMessage("§aFlight mode enabled.");
        }

        return true;
    }
}
