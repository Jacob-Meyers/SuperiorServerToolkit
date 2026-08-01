package com.jeyers.sstkit;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

///
/// Created by Jacob Meyers (TeamJEM)
/// File Created 3/18/2026
/// Last Edit    3/20/2026
///

public class HealCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, String @NonNull [] args) {
        if (args.length < 2) {
            sender.sendMessage("§cInvalid argument");
            return true;
        }

        Player target = Bukkit.getPlayerExact(args[0].toLowerCase());

        if (target == null) {
            sender.sendMessage("§cPlayer not found.");
            return true;
        }
        if (isInteger(args[1])) {
            double amount = Double.parseDouble(args[1]);
            double maxHealth = Objects.requireNonNull(target.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue();
            target.setHealth(Math.min(maxHealth, target.getHealth() + amount));
            sender.sendMessage("§aHealed " + target.getName() + " by " + args[1] + " health." +
                    "\n" + target.getName() + " now has " + target.getHealth() + " health.");
        }
        else
            sender.sendMessage("§c" + args[1] + " is not a valid integer.");

        return true;
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public List<String> onTabComplete(@NonNull CommandSender sender, @NonNull Command command,
                                      @NonNull String alias, String @NonNull [] args) {
        List<String> onlinePlayers = Bukkit.getOnlinePlayers().stream()
                .map(Player::getName)
                .toList();

        if (args.length == 1)
            return onlinePlayers;
        else
            return new ArrayList<>();
    }
}
