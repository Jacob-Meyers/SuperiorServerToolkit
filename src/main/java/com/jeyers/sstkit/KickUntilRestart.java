package com.jeyers.sstkit;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class KickUntilRestart implements CommandExecutor {
    public static List<String> permKickListRAM = new ArrayList<>();
    public static String permKickListRAMwarn;

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, String[] args) {

        if (args.length < 1) {
            sender.sendMessage("§cUsage: /kickuntilrestart <player/@a> <custom_message (optional)>");
            return true;
        } else if (args.length > 1)
            permKickListRAMwarn = java.util.Arrays
                    .stream(args, 1, args.length)
                    .collect(Collectors.joining(" "))
                    .replaceAll("&color>","§");
        else
            permKickListRAMwarn = "You have been kicked until server restarts!";

        String playerName = args[0];

        // @a support in JoinListener Class ↓
        // if (KickUntilRestart.banListRAM.contains(name) || KickUntilRestart.banListRAM.contains("@a")

        // Add to RAM list (ALWAYS EVEN IF OFFLINE)
        permKickListRAM.add(playerName.toLowerCase());
        if (playerName.equalsIgnoreCase("@a")){
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (!player.isOp())
                    player.kick(Component.text(permKickListRAMwarn));
            }
        }


        // Kick player if they're online
        Player player = Bukkit.getPlayerExact(playerName);
        if (player != null) {
            if (!player.isOp())
                player.kick(Component.text(permKickListRAMwarn));
        }

        sender.sendMessage("§aPlayer will be kicked until restart.");
        return true;
    }
}