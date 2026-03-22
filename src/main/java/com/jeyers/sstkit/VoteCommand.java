package com.jeyers.sstkit;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
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
/// File Created 3/19/2026
/// Last Edit    3/22/2026
///

public class VoteCommand implements CommandExecutor, TabCompleter {

    private final JavaPlugin plugin;

    public VoteCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, String @NonNull [] args) {
        if (!(sender instanceof Player player)) {  // Optional: only players can click links
            sender.sendMessage("§cOnly players can vote!");
            return true;
        }

        if (!plugin.getConfig().getBoolean("vote.setup")) {
            sender.sendMessage("§cVotes are not setup on this server!");
            return true;
        }

        String voteLink = plugin.getConfig().getString("vote.link");
        if (voteLink == null || voteLink.trim().isEmpty()) {
            sender.sendMessage("§cVote link is not configured!");
            return true;
        }

        // Create the clickable/hoverable message using Spigot/Bungee chat API
        TextComponent message = new TextComponent(ChatColor.GREEN + "" + ChatColor.BOLD + "[ !!! «= Click to Vote =» !!! ]");
        message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, voteLink));
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                new ComponentBuilder(ChatColor.YELLOW + "Click to Vote!")
                        .append("\n" + ChatColor.GRAY + "Opens: " + voteLink)
                        .create()));

        // Send using Spigot's method (works identically on Spigot and Paper)
        player.spigot().sendMessage(message);

        return true;
    }

    @Override
    public List<String> onTabComplete(@NonNull CommandSender sender, @NonNull Command command,
                                      @NonNull String alias, String @NonNull [] args) {
        return new ArrayList<>();
    }
}