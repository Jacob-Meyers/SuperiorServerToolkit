package com.jeyers.sstkit;

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

import java.text.DecimalFormat;
import java.util.Objects;



public class ReadPTransformCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage("§cInvalid argument");
            return true;
        }

        //sender.sendMessage("You ran /readppos!");
        Player player = Bukkit.getPlayer(args[0]);
        if (player != null) {
            Location loc = player.getLocation();
            Vector3Data rot = new Vector3Data(player.getYaw(), player.getPitch(), 0);
            Vector3Data pos = new Vector3Data(loc.getX(), loc.getY(), loc.getZ());
            if (args.length == 1) {
                pos.vector3Round(pos);
                rot.vector3Round(rot);
            } else {
                if (!Objects.equals(args[1], "exact")) {
                    sender.sendMessage("§cInvalid argument");
                    return true;
                }
            }
            DecimalFormat formatter = new DecimalFormat("#.######");
            Component posMessage = Component.text("Player " + args[0] + " position: ")
                    .append(Component.text("[" + formatter.format(pos.x) + ", " + formatter.format(pos.y) + ", " + formatter.format(pos.z) + "]")
                            .color(NamedTextColor.GREEN)
                            .hoverEvent(HoverEvent.showText(Component.text("Click to copy")))
                            .clickEvent(ClickEvent.copyToClipboard(pos.x + " " + pos.y + " " + pos.z)));
            Component rotMessage = Component.text("Player " + args[0] + " rotation: ")
                    .append(Component.text("[" + formatter.format(rot.x) + ", " + formatter.format(rot.y) + "]")
                            .color(NamedTextColor.GREEN)
                            .hoverEvent(HoverEvent.showText(Component.text("Click to copy")))
                            .clickEvent(ClickEvent.copyToClipboard(rot.x + " " + rot.y)));
            player.sendMessage(posMessage);
            player.sendMessage(rotMessage);
        } else {
            sender.sendMessage("§cPlayer does not exist or is offline.");
        }
        return true;
    }
}