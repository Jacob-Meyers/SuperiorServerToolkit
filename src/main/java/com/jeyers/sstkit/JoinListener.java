package com.jeyers.sstkit;

import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;

import static com.jeyers.sstkit.KickUntilRestartCommand.permKickListRAMwarn;

///
/// Created by Jacob Meyers (TeamJEM)
/// File Created 3/17/2026
/// Last Edit    3/19/2026
///


public class JoinListener implements Listener {

    private final JavaPlugin plugin;
    public JoinListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onAsyncPlayerPreLogin(AsyncPlayerPreLoginEvent event) {
        String uuid = event.getUniqueId().toString();

        // Check tempban
        if (plugin.getConfig().contains("tempbans." + event.getName() + "." + uuid + ".time")) {
            long unbanTime = plugin.getConfig().getLong("tempbans." + event.getName() + "." + uuid + ".time");
            long now = System.currentTimeMillis();

            if (now < unbanTime) {
                String banReason = plugin.getConfig().getString("tempbans." + event.getName() + "." + uuid + ".reason");
                String bannedBy = plugin.getConfig().getString("tempbans." + event.getName() + "." + uuid + ".bannedby");
                double remainingHours = (unbanTime - now) / (1000.0 * 60 * 60);

                event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED,
                        Component.text("§cYou are temporarily banned for another " +
                                String.format("%.2f", remainingHours) + " hours.\n\n§cReason: §b" + banReason +
                                "\n§cBanned By: §b" + bannedBy));
            } else {
                // Ban expired, remove from config
                plugin.getConfig().set("tempbans." + event.getName(), null);
                plugin.saveConfig();
            }
        }

        // Example: permKick check
        String name = event.getName().toLowerCase();
        if (KickUntilRestartCommand.permKickListRAM.contains(name) || KickUntilRestartCommand.permKickListRAM.contains("@a")) {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER,
                    Component.text(permKickListRAMwarn));
        }
    }
}