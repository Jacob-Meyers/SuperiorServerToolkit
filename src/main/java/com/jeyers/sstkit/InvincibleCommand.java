package com.jeyers.sstkit;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NonNull;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

///
/// Created by Jacob Meyers (TeamJEM)
/// File Created 3/18/2026
/// Last Edit    3/19/2026
///


public class InvincibleCommand implements CommandExecutor {
    public static final Set<UUID> invinciblePlayers = new HashSet<>();

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, String @NonNull [] args) {
        Player player;
        if (args.length < 1) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("§cThis command can only be used by players.");
                return true;
            }
            player = ((Player) sender).getPlayer();
        } else {
            player = Bukkit.getPlayer(args[0]);
        }

        if (player==null) {
            sender.sendMessage("§cPlayer not found.");
            return true;
        }
        boolean invincible = invinciblePlayers.contains(player.getUniqueId());
        if (invincible) {
            invinciblePlayers.remove(player.getUniqueId());
            player.sendMessage("You are no longer invincible.");
        } else {
            invinciblePlayers.add(player.getUniqueId());
            player.sendMessage("You are now invincible!");
        }

        return true;
    }

}
