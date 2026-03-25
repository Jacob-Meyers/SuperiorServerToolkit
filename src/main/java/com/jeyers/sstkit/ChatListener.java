package com.jeyers.sstkit;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@SuppressWarnings("deprecation")
public class ChatListener implements Listener {

    @SuppressWarnings("FieldCanBeLocal")
    private final JavaPlugin plugin;
    private final Set<UUID> supporterUUIDs;
    private final String format;

    public ChatListener(JavaPlugin plugin) {
        this.plugin = plugin;

        supporterUUIDs = plugin.getConfig().getStringList("supporter-chat.players")
                .stream()
                .map(UUID::fromString)
                .collect(Collectors.toSet());

        format = ChatColor.translateAlternateColorCodes('&',
                plugin.getConfig().getString("supporter-chat.format", "<{playerName}> &a"));
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if (!supporterUUIDs.contains(player.getUniqueId())) {
            return;
        }

        event.setFormat(format.replace("{playerName}", player.getName()) + "%2$s");
    }
}