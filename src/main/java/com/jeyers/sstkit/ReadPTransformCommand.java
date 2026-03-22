package com.jeyers.sstkit;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.event.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import org.jspecify.annotations.NonNull;

import java.util.Objects;

import static com.jeyers.sstkit.Vector2Data.vector2ToString;
import static com.jeyers.sstkit.Vector3Data.vector3ToString;

///
/// Created by Jacob Meyers (TeamJEM)
/// File Created 3/16/2026
/// Last Edit    3/21/2026
///


public class ReadPTransformCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage("§cInvalid argument");
            return true;
        }

        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);
        if (offlinePlayer.isOnline()) {
            Player player = Bukkit.getPlayerExact(args[0].toLowerCase());
            assert player != null;
            Location loc = player.getLocation();
            Vector3Data pos = new Vector3Data(loc.getX(), loc.getY(), loc.getZ());
            Vector2Data rot = new Vector2Data(loc.getYaw(), loc.getPitch());
            if (args.length == 1) {
                pos.round();
                rot.round();
            } else {
                if (!Objects.equals(args[1], "exact")) {
                    sender.sendMessage("§cInvalid argument");
                    return true;
                }
            }
            Component posMessage = Component.text("Player " + player.getName() + " position: ")
                    .append(Component.text("[" + vector3ToString(pos, ", ") + "]")
                            .color(NamedTextColor.GREEN)
                            .hoverEvent(HoverEvent.showText(Component.text("Click to copy")))
                            .clickEvent(ClickEvent.copyToClipboard(vector3ToString(pos, " "))));
            Component rotMessage = Component.text("Player " + player.getName() + " rotation: ")
                    .append(Component.text("[" + vector2ToString(rot, ", ") + "]")
                            .color(NamedTextColor.GREEN)
                            .hoverEvent(HoverEvent.showText(Component.text("Click to copy")))
                            .clickEvent(ClickEvent.copyToClipboard(vector2ToString(rot, " "))));
            sender.sendMessage(posMessage);
            sender.sendMessage(rotMessage);
        } else {
            sender.sendMessage("§cPlayer not found!");
        }
        return true;
    }
}