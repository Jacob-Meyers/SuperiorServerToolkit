package com.jeyers.sstkit;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

///
/// Created by Jacob Meyers (TeamJEM)
/// File Created 3/17/2026
/// Last Edit    3/20/2026
///


public class GivePermCommand implements CommandExecutor, TabCompleter {

    private final JavaPlugin plugin;

    public GivePermCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    private final List<String> givePermCommandList = new ArrayList<>(Arrays.asList(
            "fly",
            "ghost",
            "heal"
    ));

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, String[] args) {
        if (args.length < 2) {
            sender.sendMessage("§cUsage: /giveperm <player> <command>");
            return true;
        }

        Player target = Bukkit.getPlayerExact(args[0].toLowerCase());

        if (target == null) {
            sender.sendMessage("§cPlayer not found.");
            return true;
        }

        String selectedCommand = args[1].toLowerCase();
        if (givePermCommandList.contains(selectedCommand)){
            target.addAttachment(plugin, "sstkit.permission."+selectedCommand, true);
            sender.sendMessage("§aGiven /"+selectedCommand+" permission to " + target.getName());
        } else
            sender.sendMessage("§cNot an available command to give perm.");

        return true;
    }

    @Override
    public List<String> onTabComplete(@NonNull CommandSender sender, @NonNull Command command,
                                      @NonNull String alias, String @NonNull [] args) {
        List<String> onlinePlayers = Bukkit.getOnlinePlayers().stream()
                .map(Player::getName)
                .toList();

        if (args.length == 1)
            return onlinePlayers;
        if (args.length == 2)
            return givePermCommandList;
        else
            return new ArrayList<>();
    }
}