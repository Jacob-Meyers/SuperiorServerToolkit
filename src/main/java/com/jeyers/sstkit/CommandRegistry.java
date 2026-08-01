package com.jeyers.sstkit;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandRegistry {

    private final JavaPlugin plugin;

    public CommandRegistry(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void register(String name, CommandExecutor executor, TabCompleter tabCompleter) {
        PluginCommand command = plugin.getCommand(name);
        if (command != null) {
            command.setExecutor(executor);
            if (tabCompleter != null) {
                command.setTabCompleter(tabCompleter);
            }
        } else {
            plugin.getLogger().warning("Failed to register command '" + name + "' because it is missing from plugin.yml!");
        }
    }

    public void register(String name, CommandExecutor executor) {
        TabCompleter tabCompleter = (executor instanceof TabCompleter) ? (TabCompleter) executor : null;
        register(name, executor, tabCompleter);
    }
}