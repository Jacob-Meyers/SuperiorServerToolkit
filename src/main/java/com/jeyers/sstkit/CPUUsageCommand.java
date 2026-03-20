package com.jeyers.sstkit;

import com.sun.management.OperatingSystemMXBean;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jspecify.annotations.NonNull;

import java.lang.management.ManagementFactory;

///
/// Created by Jacob Meyers (TeamJEM)
/// File Created 3/18/2026
/// Last Edit    3/19/2026
///

public class CPUUsageCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, String @NonNull [] args) {
        double cpuUsage = getProcessCpuLoad() * 100; // percentage
        String cpuUsageString = String.format("CPU Usage: %.2f%%", cpuUsage);
        sender.sendMessage(cpuUsageString);

        return true;
    }

    public double getProcessCpuLoad() {
        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
        return osBean.getProcessCpuLoad();
    }
}
