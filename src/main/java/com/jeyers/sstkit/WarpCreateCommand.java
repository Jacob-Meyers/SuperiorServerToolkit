package com.jeyers.sstkit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

///
/// Created by Jacob Meyers (TeamJEM)
/// File Created 3/18/2026
/// Last Edit    3/19/2026
///



public class WarpCreateCommand implements CommandExecutor, TabCompleter {

    private final JavaPlugin plugin;
    public WarpCreateCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, String @NonNull [] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cOnly players can use this command!");
            return true;
        }
        if (args.length < 1) {
            sender.sendMessage("§cWarp location not defined.");
            return true;
        }

        if (!plugin.getConfig().getBoolean("warp.setup"))
            sender.sendMessage("§eWARNING: Warps are marked as NOT setup in the config.");
        plugin.getConfig().set("warp.warps." + args[0], player.getLocation());

        try {
            plugin.saveConfig();
            sender.sendMessage("§aWarp location " + args[0] + " created!");
        } catch (Exception e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(@NonNull CommandSender sender, @NonNull Command command,
                                      @NonNull String alias, String @NonNull [] args) {
        return new ArrayList<>();
    }
}
