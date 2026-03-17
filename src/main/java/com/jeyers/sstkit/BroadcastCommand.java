package com.jeyers.sstkit;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jspecify.annotations.NonNull;

import java.util.Objects;

public class BroadcastCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, String[] args) {
        if (args.length == 0) {
            sender.sendRichMessage("<red>You cannot send an empty broadcast!");
            return true;
        }

        String[] msgArgs = args;
        String broadcastPrefix = "[BROADCAST] ";
        if (Objects.equals(args[0], "false")) {
            broadcastPrefix = "";
            int originalLength = args.length;
            msgArgs = new String[originalLength - 1];
            System.arraycopy(args, 1, msgArgs, 0, originalLength - 1);
        }

        final String message = String.join(" ", msgArgs);
        final Component finalMessage = Component.text(broadcastPrefix + message.replace("&color>","§"));
        Bukkit.broadcast(finalMessage);
        return true;
    }
}
