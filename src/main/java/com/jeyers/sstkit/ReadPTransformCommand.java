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

import static com.jeyers.sstkit.Vector2Data.vector2ToString;
import static com.jeyers.sstkit.Vector3Data.vector3ToString;


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
            Vector3Data pos = new Vector3Data(loc.getX(), loc.getY(), loc.getZ());
            Vector2Data rot = new Vector2Data(player.getYaw(), player.getPitch());
            if (args.length == 1) {
                pos.vector3Round(pos);
                rot.vector2Round(rot);
            } else {
                if (!Objects.equals(args[1], "exact")) {
                    sender.sendMessage("§cInvalid argument");
                    return true;
                }
            }
            DecimalFormat formatter = new DecimalFormat("#.######");

            Component posMessage = Component.text("Player " + args[0] + " position: ")
                    .append(Component.text("[" + vector3ToString(pos, ", ") + "]")
                            .color(NamedTextColor.GREEN)
                            .hoverEvent(HoverEvent.showText(Component.text("Click to copy")))
                            .clickEvent(ClickEvent.copyToClipboard(vector3ToString(pos, " "))));
            Component rotMessage = Component.text("Player " + args[0] + " rotation: ")
                    .append(Component.text("[" + vector2ToString(rot, ", ") + "]")
                            .color(NamedTextColor.GREEN)
                            .hoverEvent(HoverEvent.showText(Component.text("Click to copy")))
                            .clickEvent(ClickEvent.copyToClipboard(vector2ToString(rot, " "))));
            player.sendMessage(posMessage);
            player.sendMessage(rotMessage);
        } else {
            sender.sendMessage("§cPlayer does not exist or is offline.");
        }
        return true;
    }
}