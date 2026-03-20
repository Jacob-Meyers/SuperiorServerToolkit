package com.jeyers.sstkit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jspecify.annotations.NonNull;

///
/// Created by Jacob Meyers (TeamJEM)
/// File Created 3/17/2026
/// Last Edit    3/19/2026
///


public class KickUntilRestartClearCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, @NonNull Command command, @NonNull String label, String @NonNull [] args) {
        KickUntilRestartCommand.permKickListRAM.clear();
        sender.sendMessage("§aKick-until-restart list cleared.");
        return true;
    }
}