package com.jeyers.sstkit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;
import org.jspecify.annotations.NonNull;

public class WarpListCommand implements CommandExecutor {

    private final JavaPlugin plugin;
    public WarpListCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, String @NonNull [] args) {
        if (plugin.getConfig().getBoolean("warpPermission.opOnly") && !sender.isOp()){
            sender.sendMessage("You do not have permission to use this command");
            return true;
        }

        ConfigurationSection section = plugin.getConfig().getConfigurationSection("warps");
        if (section == null || section.getKeys(false).isEmpty()) {
            sender.sendMessage("§cNo warps found.");
            return true;
        }
        String result = String.join(",\n   ", section.getKeys(false));
        sender.sendMessage("Available Warps:\n   " + result);

        return true;
    }
}
