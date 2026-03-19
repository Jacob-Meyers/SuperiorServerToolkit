package com.jeyers.sstkit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jspecify.annotations.NonNull;

import static com.jeyers.sstkit.SupToolkit.commandList;

public class CommandsListCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, String @NonNull [] args) {
        String result = String.join(",\n   ", commandList);
        sender.sendMessage("§aSuperior Server Toolkit Commands: §e\n   " + result);
        return true;
    }

}
