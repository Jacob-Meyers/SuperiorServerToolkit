package com.jeyers.sstkit;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jspecify.annotations.NonNull;

public class GivePermCommand implements CommandExecutor {

    private final JavaPlugin plugin;

    public GivePermCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, String[] args) {
        if (args.length < 2) {
            sender.sendMessage("§cUsage: /giveperm <player> <command>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            sender.sendMessage("§cPlayer not found.");
            return true;
        }

        switch (args[1].toLowerCase()) {
            case "fly":
                target.addAttachment(plugin, "sstkit.permission.fly", true);
                sender.sendMessage("§aGiven /fly permission to " + target.getName());
                break;
            case "ghost":
                target.addAttachment(plugin, "sstkit.permission.ghost", true);
                sender.sendMessage("§aGiven /ghost permission to " + target.getName());
                break;
            default:
                sender.sendMessage("§cNot an available command to give perm.");
                break;
        }

        return true;
    }
}