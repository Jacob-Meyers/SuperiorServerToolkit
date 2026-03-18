package com.jeyers.sstkit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jspecify.annotations.NonNull;

public class MemoryUsageCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, String @NonNull [] args) {
        Runtime runtime = Runtime.getRuntime();

        long maxMemory = runtime.maxMemory();
        long allocatedMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long usedMemory = allocatedMemory - freeMemory;

        double toMB = 1024 * 1024;
        String memoryUsage = String.format("Memory: %.2f MB / %.2f MB", usedMemory / toMB, maxMemory / toMB);
        String memoryUsageGB = String.format("Memory: %.6f GB / %.6f GB", (usedMemory/toMB)/1024, (maxMemory/toMB)/1024);
        sender.sendMessage(memoryUsage);
        sender.sendMessage(memoryUsageGB);

        return true;
    }
}
