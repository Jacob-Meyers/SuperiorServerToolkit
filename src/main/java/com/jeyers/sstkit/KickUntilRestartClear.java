package com.jeyers.sstkit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jspecify.annotations.NonNull;


public class KickUntilRestartClear implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, @NonNull Command command, @NonNull String label, String @NonNull [] args) {
        KickUntilRestart.permKickListRAM.clear();
        sender.sendMessage("§aKick-until-restart list cleared.");
        return true;
    }
}