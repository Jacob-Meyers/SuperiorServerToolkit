package com.jeyers.sstkit;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

import static com.jeyers.sstkit.CombatListener.combatTagged;
import static com.jeyers.sstkit.SupToolkit.COMBAT_TIME_HOME;

///
/// Created by Jacob Meyers (TeamJEM)
/// File Created 3/20/2026
/// Last Edit    3/21/2026
///


public class HomeCommand implements CommandExecutor, TabCompleter {

    private final JavaPlugin plugin;
    public HomeCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, String @NonNull [] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cOnly players can use this command!");
            return true;
        }

        ConsoleCommandSender console = Bukkit.getConsoleSender();

        Long lastCombat = combatTagged.get(player.getUniqueId());
        if (lastCombat != null && (System.currentTimeMillis() - lastCombat) < COMBAT_TIME_HOME) {
            player.sendMessage("§cYou cannot run command /home while in combat!");
            long remainingSeconds = Math.max(0, (COMBAT_TIME_HOME - (System.currentTimeMillis() - lastCombat)) / 1000);
            player.sendMessage("§bYou are detected in combat for another §c" + remainingSeconds + "§b seconds!");
            return true;
        }

        final Location newLocation = plugin.getConfig().getLocation("homes.locations." + player.getName());
        if (newLocation != null){
            player.teleport(newLocation);
            sender.sendMessage("§aTeleported to your home.");
            console.sendMessage(player.getName() + " teleported to thier home.");
        } else
            sender.sendMessage("§cHome location not set!");

        return true;
    }


    @Override
    public List<String> onTabComplete(@NonNull CommandSender sender, @NonNull Command command,
                                      @NonNull String alias, String @NonNull [] args) {
        return new ArrayList<>();
    }
}
