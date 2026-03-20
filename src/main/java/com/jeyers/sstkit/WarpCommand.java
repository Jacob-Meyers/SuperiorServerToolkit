package com.jeyers.sstkit;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.ConfigurationSection;
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


public class WarpCommand implements CommandExecutor, TabCompleter {

    private final JavaPlugin plugin;
    public WarpCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, String @NonNull [] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cOnly players can use this command!");
            return true;
        }

        if (!plugin.getConfig().getBoolean("warp.setup")){
            sender.sendMessage("§cWarps are not setup on this server!");
            return true;
        } else if (plugin.getConfig().getBoolean("warp.opOnlyPerm") && !sender.isOp()){
            sender.sendMessage("You do not have permission to use this command");
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage("§cWarp location not defined.");
            return true;
        }

        final Location newLocation = plugin.getConfig().getLocation("warp.warps." + args[0]);
        if (newLocation != null){
            player.teleport(newLocation);
            sender.sendMessage("§aWarped to "+args[0]);
        } else
            sender.sendMessage("§cWarp location does not exist.");

        try {
            plugin.saveConfig();
        } catch (Exception e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
        return true;
    }


    @Override
    public List<String> onTabComplete(@NonNull CommandSender sender, @NonNull Command command,
                                      @NonNull String alias, String @NonNull [] args) {

        List<String> completions = new ArrayList<>();

        if (args.length <= 1) {
            ConfigurationSection section = plugin.getConfig().getConfigurationSection("warp.warps");

            if (section != null) {
                for (String warp : section.getKeys(false)) {
                    if (warp.toLowerCase().startsWith(args[0].toLowerCase())) {
                        completions.add(warp);
                    }
                }
            }
        }

        return completions;
    }
}
