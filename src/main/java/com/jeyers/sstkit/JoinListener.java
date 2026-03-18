package com.jeyers.sstkit;

import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import static com.jeyers.sstkit.KickUntilRestartCommand.permKickListRAMwarn;


public class JoinListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        String name = event.getPlayer().getName().toLowerCase();

        if (KickUntilRestartCommand.permKickListRAM.contains(name) || KickUntilRestartCommand.permKickListRAM.contains("@a")) {
            if (!event.getPlayer().isOp())
                event.getPlayer().kick(Component.text(permKickListRAMwarn));
        }

    }
}