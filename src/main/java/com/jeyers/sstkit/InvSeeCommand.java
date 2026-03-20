package com.jeyers.sstkit;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;


///
/// Created by Jacob Meyers (TeamJEM)
/// File Created 3/18/2026
/// Last Edit    3/19/2026
///


public class InvSeeCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, String @NonNull [] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cThis command can only be used by players.");
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage("§cInvalid argument");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target != null) {
            if (target.getName().equals(sender.getName())) {
                sender.sendMessage("§c" + label.toLowerCase() + " cannot be used on self.");
                return true;
            }
            if (!label.equalsIgnoreCase("invsee"))
                player.openInventory(target.getInventory());
            else
                player.openInventory(target.getEnderChest());
        } else
            sender.sendMessage("§cPlayer not found.");

        return true;
    }

    @Override
    public List<String> onTabComplete(@NonNull CommandSender sender, @NonNull Command command,
                                      @NonNull String alias, String @NonNull [] args) {
        List<String> onlinePlayers = new ArrayList<>(Bukkit.getOnlinePlayers().stream()
                .map(Player::getName)
                .toList());

        onlinePlayers.remove(sender.getName());

        if (args.length == 1)
            return onlinePlayers;
        else
            return new ArrayList<>();
    }
}
